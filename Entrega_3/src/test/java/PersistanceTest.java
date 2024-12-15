import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.DistribucionDeViandas;
import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.Colaboraciones.DonacionDeVianda;
import org.example.Dominio.Colaboraciones.HacerseCargoDeHeladera;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.example.Dominio.Colaboraciones.RegistrarPersonasEnSituacionVulnerable;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Persona.PersonaJuridica;
import org.example.Dominio.Persona.TipoJuridica;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.PersonaVulnerable;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Tarjetas.TarjetaVulnerable;
import org.example.Dominio.Viandas.EstadoVianda;
import org.example.Dominio.Viandas.Vianda;
import org.example.Validador.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.example.Dominio.Incidentes.TipoAlerta.TEMPERATURA;

public class PersistanceTest {
@Test
public void persistirViandaFechasErróneas() { //la fecha de vencimiento es menor a la de fabricacion
    PuntoEstrategico punto = new PuntoEstrategico("p2", 13.0, 15.0, "Av Libertador 1200");
    Heladera heladera = new Heladera(16, 2, punto,10);

    LocalDate fechaFabricacion = LocalDate.of(2024, 12, 1); // Fecha futura
    LocalDate fechaVencimiento = LocalDate.of(2024, 11, 1); // Fecha anterior a fabricación

    Vianda vianda = new Vianda("Pastas", fechaVencimiento, fechaFabricacion, heladera, 100, 10, EstadoVianda.NO_ENTREGADA, null);

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    Assertions.assertThrows(RuntimeException.class, () -> {
        em.persist(punto);
        em.persist(heladera);
        em.persist(vianda);
        BDUtils.commit(em);
    });
}

@Test
public void persistirHeladeraDatosInvalidos() { //los datos ingresados en punto estrategico son negativos
    PuntoEstrategico punto = new PuntoEstrategico("p1", 1000.0, -2000.0, "DirecciónNoVálida");
    Heladera heladera = new Heladera(-10, 1000, punto,10); // Capacidad negativa y nivel extremo

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    Assertions.assertThrows(RuntimeException.class, () -> {
        em.persist(punto);
        em.persist(heladera);
        BDUtils.commit(em);
    });
}
@Test
public void persistirRegPVulnerableMenoresACargoExtremos() {//Una persona no podria tener tanto menores a cargo
    PersonaVulnerable per = new PersonaVulnerable();
    per.setFechaDeRegristro(LocalDate.now());
    per.setMenoresACargo(100); // Límite extremo

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    Assertions.assertThrows(RuntimeException.class, () -> {
        em.persist(per);
        BDUtils.commit(em);
    });
}
 

  @Test
  public void persistirColaboracionOfertayRegPer(){

    
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    Usuario usuario1 = new Usuario("juli","juli123");
    
    em.persist(usuario1);

    PersonaJuridica personaJud1 = new PersonaJuridica();
    
    personaJud1.setUsuario(usuario1);
    personaJud1.setDireccion("Av Montes de Oca 1111");
    personaJud1.setRazonSocial("SA");
    personaJud1.setTipo(TipoJuridica.EMPRESA);

    em.persist(personaJud1);

    Colaborador colab = new Colaborador();
    em.persist(colab);
    personaJud1.asignarRol(personaJud1, colab);

   OfrecerProductos prodSinNombre = new OfrecerProductos(null, "Villavicencio", 100);
   Assertions.assertThrows(IllegalArgumentException.class, () -> {
       em.persist(prodSinNombre); // Producto sin nombre, debería fallar
   });

   OfrecerProductos prodNegativa = new OfrecerProductos("Agua", "Villavicencio", -100);
   Assertions.assertThrows(IllegalArgumentException.class, () -> {
       em.persist(prodNegativa); // Cantidad negativa, debería fallar
   });

    BDUtils.commit(em);

    Assertions.assertEquals(1,1);
  }

@Test
public void persistirUsuarioDatosExtremos() { //datos erroreonos o vacios donde no podria haber 
    Usuario usuario1 = new Usuario("", "1234"); // Usuario sin nombre
    Documento documento1 = new Documento(0, "", "", ""); // Documento vacío

    PersonaHumana personaHumana1 = new PersonaHumana();
    personaHumana1.setUsuario(usuario1);
    personaHumana1.setDireccion(""); // Dirección vacía
    personaHumana1.setCuil("cuilInválido"); // CUIL incorrecto
    personaHumana1.setApellido("ApellidoExtremadamenteLargoQuePodriaCausarErroresAlPersistirEnBD");
    personaHumana1.setNombre("Nombre");
    personaHumana1.setFechaDeNacimiento(LocalDate.of(1800, 1, 1)); // Fecha muy antigua
    personaHumana1.setDocumento(documento1);

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    Assertions.assertThrows(RuntimeException.class, () -> {
        em.persist(usuario1);
        em.persist(documento1);
        em.persist(personaHumana1);
        BDUtils.commit(em);
    });
}

@Test
public void fallaTecnicaHeladeraInactiva() {
    PuntoEstrategico punto = new PuntoEstrategico("pErr", 10.0, 12.0, "Av Santa Fe 1200");
    Heladera heladera = new Heladera(10, 1, punto,10);
    heladera.setEstado(EstadoHeladera.INACTIVA); // Estado de heladera en inactiva, no se puede registrar una falla

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    em.persist(punto);
    em.persist(heladera);

    Usuario usuario1 = new Usuario("ianSus", "iannn");
    Documento documento1 = new Documento(33332, "3", "m", "m");
    em.persist(usuario1);
    em.persist(documento1);

    PersonaHumana personaHumana1 = new PersonaHumana();
    personaHumana1.setUsuario(usuario1);
    personaHumana1.setDireccion("Av Pedro Goyena 1111");
    personaHumana1.setCuil("632113");
    personaHumana1.setApellido("Feldman");
    personaHumana1.setNombre("IA");
    personaHumana1.setFechaDeNacimiento(LocalDate.of(2003, 06, 10));
    personaHumana1.setDocumento(documento1);
    em.persist(personaHumana1);

    Colaborador colab = new Colaborador();
    em.persist(colab);
    personaHumana1.asignarRol(personaHumana1, colab);

    
    FallaTecnica fallaTecnica = new FallaTecnica(colab, heladera, LocalDateTime.of(2024, 11, 14, 15, 30));
    fallaTecnica.setDescription("Falla en heladera inactiva");

  
    Assertions.assertThrows(RuntimeException.class, () -> {
        em.persist(fallaTecnica);
        BDUtils.commit(em); 
    });
}
/*
La heladera está en estado "INACTIVA", lo que debería impedir la persistencia de una falla técnica en ella. Esto puede generar una excepción IllegalStateException si se implementa una validación para evitar que se registren fallas en dispositivos inactivos.
*/

}
