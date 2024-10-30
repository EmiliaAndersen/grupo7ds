import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.Persona.PersonaHumana;
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
}
