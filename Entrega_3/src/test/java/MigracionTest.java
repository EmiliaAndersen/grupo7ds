import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Migrador.Migrador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MigracionTest {
    @BeforeEach
    public void setUp() {

    }

    @Test
    public void migracion_archivo_csv_mails() {
        // --- EJEMPLO MIGRACION ---
        Migrador reg = new Migrador();
        List<PersonaHumana> personas = new ArrayList<>();
        List<Colaborador> colaboradores = new ArrayList<>();
        reg.Migrar("../Entrega_3/src/main/java/org/example/archivo.csv", personas, colaboradores);
        // Chequeo que haya salido todo bien

        List<String> mailsPrueba = Arrays.asList("juan@example.com", "maria@example.com", "pedro@example.com");


        System.out.println(colaboradores.size());

        int i = 0;
        for (Colaborador colaborador : colaboradores) {
            Persona persona = colaborador.getPersona();
            if (persona != null) {
                List<MedioDeContacto> medios = persona.getMediosDeContacto();
                MedioDeContacto contacto = null;
                if (medios != null && !medios.isEmpty()) {
                    contacto = medios.get(0);
                    System.out.println(contacto.getDetalle());
                } else {
                    System.out.println("La persona no tiene medios de contacto registrados.");
                }
                System.out.println(colaborador.getColaboraciones());
                assert contacto != null;
                Assertions.assertEquals(contacto.getDetalle(), mailsPrueba.get(i));
            } else {
                System.out.println("El colaborador no tiene persona asignada.");
            }
            i++;
        }
    }



}
