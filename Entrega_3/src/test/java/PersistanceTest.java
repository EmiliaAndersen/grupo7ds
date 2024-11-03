import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.DistribucionDeViandas;
import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.Colaboraciones.DonacionDeVianda;
import org.example.Dominio.Colaboraciones.HacerseCargoDeHeladera;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.example.Dominio.Colaboraciones.RegistrarPersonasEnSituacionVulnerable;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Persona.PersonaJuridica;
import org.example.Dominio.Persona.TipoJuridica;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.PersonaVulnerable;
import org.example.Dominio.Tarjetas.TarjetaVulnerable;
import org.example.Dominio.Viandas.EstadoVianda;
import org.example.Dominio.Viandas.Vianda;
import org.example.Validador.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class PersistanceTest {

  @Test
  public void persistirUsuario() {
    Usuario usuario1 = new Usuario("dan","1234");
    Documento documento1 = new Documento(1123,"d","das","dasda");

    PersonaHumana personaHumana1 = new PersonaHumana();
    personaHumana1.setUsuario(usuario1);
    personaHumana1.setDireccion("ghoalsfas");
    personaHumana1.setCuil("12312");
    personaHumana1.setApellido("asdfa");
    personaHumana1.setNombre("asdfasfsa");
    personaHumana1.setFechaDeNacimiento(LocalDate.of(1990, 5, 15));
    personaHumana1.setDocumento(documento1);

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    em.persist(usuario1);
    em.persist(documento1);
    em.persist(personaHumana1);
    BDUtils.commit(em);

    //Verificar si en la tabla se genero lo anterior :)
    Assertions.assertEquals(1,1);
  }

  @Test
  public void persistirHeladera(){

    PuntoEstrategico punto = new PuntoEstrategico("p1", 10.0,12.0, "Av Santa Fe 1200");
    Heladera heladera = new Heladera(10,1,punto);

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    em.persist(punto);
    em.persist(heladera);
    BDUtils.commit(em);

    Assertions.assertEquals(1,1);
  }

  @Test
  public void persistirVianda(){
    
    PuntoEstrategico punto = new PuntoEstrategico("p2", 13.0,15.0, "Av Libertador 1200");
    Heladera heladera = new Heladera(16,2,punto);
    
    LocalDate fecha1 = LocalDate.of(2024, 11, 3); // Año, Mes, Día
    LocalDate fecha2 = LocalDate.of(2024, 11, 6); 


    Vianda vianda = new Vianda("Pastas", fecha2 ,fecha1, heladera, 100, 10, EstadoVianda.NO_ENTREGADA);
    
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    em.persist(punto);
    em.persist(heladera);
    em.persist(vianda);
    BDUtils.commit(em);

    Assertions.assertEquals(1,1);
  }


  @Test
  public void persistirColaboracionViandas(){

    
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    Usuario usuario1 = new Usuario("emi","123456789");
    Documento documento1 = new Documento(1124,"e","f","f");

    
    em.persist(usuario1);
    em.persist(documento1);

    PersonaHumana personaHumana1 = new PersonaHumana();
    personaHumana1.setUsuario(usuario1);
    personaHumana1.setDireccion("Av Coronel Diaz 1111");
    personaHumana1.setCuil("12341");
    personaHumana1.setApellido("Andersen");
    personaHumana1.setNombre("Emilia");
    personaHumana1.setFechaDeNacimiento(LocalDate.of(2003, 12, 23));
    personaHumana1.setDocumento(documento1);


    em.persist(personaHumana1);

    Colaborador colab = new Colaborador();
    em.persist(colab);
    personaHumana1.asignarRol(personaHumana1, colab);
    
    PuntoEstrategico punto = new PuntoEstrategico("p4", 10.0,15.0, "Av Medrano 850");
    PuntoEstrategico punto2 = new PuntoEstrategico("p3", 23.0,5.0, "Av Pueyrredon 1200");

    em.persist(punto);
    em.persist(punto2);

    Heladera heladera1 = new Heladera(10,2,punto);
    Heladera heladera2 = new Heladera(14,3,punto2);

    
    em.persist(heladera1);
    em.persist(heladera2);

    LocalDate fecha1 = LocalDate.of(2024, 11, 3); // Año, Mes, Día
    LocalDate fecha2 = LocalDate.of(2024, 12, 6); 

    Vianda vianda = new Vianda("Estofado", fecha2 ,fecha1, heladera1, 100, 10, EstadoVianda.ENTREGADA);
    em.persist(vianda);
    
    DonacionDeVianda don = new DonacionDeVianda(vianda);
    don.setColaborador(colab);
    DistribucionDeViandas dist= new DistribucionDeViandas(heladera1, heladera2, "No hay espacio", fecha1);
    dist.setColaborador(colab);

    DonacionDeDinero donDinero = new DonacionDeDinero(fecha1, (double) 109000,fecha2);
    donDinero.setColaborador(colab);

    colab.agregarColaboracion(don);
    colab.agregarColaboracion(dist);
    colab.agregarColaboracion(donDinero);


    em.persist(don);
    em.persist(dist);
    em.persist(donDinero);


    BDUtils.commit(em);

    Assertions.assertEquals(1,1);
  }

  @Test
  public void persistirColaboracionHacerseCargoHeladera(){

    
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    Usuario usuario1 = new Usuario("cami","cami123");
    Documento documento1 = new Documento(332,"3","3","f");

    
    em.persist(usuario1);
    em.persist(documento1);

    PersonaHumana personaHumana1 = new PersonaHumana();
    personaHumana1.setUsuario(usuario1);
    personaHumana1.setDireccion("Av Rivadav 1111");
    personaHumana1.setCuil("12351");
    personaHumana1.setApellido("Camila");
    personaHumana1.setNombre("Solimano");
    personaHumana1.setFechaDeNacimiento(LocalDate.of(2003, 02 , 23));
    personaHumana1.setDocumento(documento1);

    em.persist(personaHumana1);

    Colaborador colab = new Colaborador();
    em.persist(colab);
    personaHumana1.asignarRol(personaHumana1, colab);

    

    HacerseCargoDeHeladera cargo = new HacerseCargoDeHeladera();
    colab.agregarColaboracion(cargo);
    cargo.setColaborador(colab);

    em.persist(cargo);

    BDUtils.commit(em);

    Assertions.assertEquals(1,1);
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


    OfrecerProductos prod = new OfrecerProductos("Agua","Villavicencio",100);
    colab.agregarColaboracion(prod);
    prod.setColaborador(colab);

    em.persist(prod);

    BDUtils.commit(em);

    Assertions.assertEquals(1,1);
  }

  @Test
  public void persistirRegPVulnerable(){

    
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    Usuario usuario1 = new Usuario("ian","iannn");
    Documento documento1 = new Documento(33332,"3","m","m");

    
    em.persist(usuario1);
    em.persist(documento1);

    PersonaHumana personaHumana1 = new PersonaHumana();
    personaHumana1.setUsuario(usuario1);
    personaHumana1.setDireccion("Av Pedro Goyena 1111");
    personaHumana1.setCuil("632113");
    personaHumana1.setApellido("Feldman");
    personaHumana1.setNombre("IA");
    personaHumana1.setFechaDeNacimiento(LocalDate.of(2003, 06 , 10));
    personaHumana1.setDocumento(documento1);

    em.persist(personaHumana1);

    Colaborador colab = new Colaborador();
    em.persist(colab);
    personaHumana1.asignarRol(personaHumana1, colab);

    Usuario usuario2 = new Usuario("pepito","pep");
    Documento documento2 = new Documento(33444,"2","m","m");

    
    em.persist(usuario2);
    em.persist(documento2);

    PersonaHumana personaHumana2 = new PersonaHumana();
    personaHumana2.setUsuario(usuario2);
    personaHumana2.setDireccion("Av Pedro Goyena 1111");
    personaHumana2.setCuil("677777");
    personaHumana2.setApellido("Pep");
    personaHumana2.setNombre("Pepito");
    personaHumana2.setFechaDeNacimiento(LocalDate.of(2000, 06 , 10));
    personaHumana2.setDocumento(documento2);

    em.persist(personaHumana2);

    LocalDate fecha1 = LocalDate.of(2024, 11, 3); // Año, Mes, Día
    PersonaVulnerable per = new PersonaVulnerable();
    per.setFechaDeRegristro(fecha1);
    per.setMenoresACargo(1);
   
    TarjetaVulnerable tarj = new TarjetaVulnerable(per);
    per.setTarjetaVulnerable(tarj);   
    em.persist(tarj);

    em.persist(per);
    personaHumana2.asignarRol(personaHumana2, per);


    RegistrarPersonasEnSituacionVulnerable reg = new RegistrarPersonasEnSituacionVulnerable(per);
    colab.agregarColaboracion(reg);
    reg.setColaborador(colab);

    em.persist(reg);

    BDUtils.commit(em);

    Assertions.assertEquals(1,1);
  }


}
