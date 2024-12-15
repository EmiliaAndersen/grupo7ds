import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Viandas.Vianda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.Dominio.Suscripciones.TipoSuscripcion.RESTANTES;

class SuscriptorTest {

    @Test
    void testSuscripcion() {
        Colaborador colaborador = new Colaborador();
        PuntoEstrategico punto = new PuntoEstrategico("zona1", 10.0, 120.0, "Av Diaz 12");
        Heladera heladera = new Heladera(10, 0, punto,100);
        Suscriptor suscriptor = new Suscriptor();
        suscriptor.setColaborador(colaborador);
        heladera.agregarSuscriptor(suscriptor);

        Vianda vianda1 = new Vianda(null, null, null, null, 0, 0, null,null);
        Vianda vianda2 = new Vianda(null, null, null, null, 0, 0, null,null);
        heladera.agregarVianda(vianda1);
        heladera.agregarVianda(vianda2);

        //Notifico
        heladera.suscriptores.get(0).notificarSuscriptor();


        Assertions.assertEquals(heladera.suscriptores.get(0).getMensajesEnviados().size(),1);
    }
}
