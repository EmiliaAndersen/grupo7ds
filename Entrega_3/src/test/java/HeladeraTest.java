import org.example.Dominio.Incidentes.*;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;

import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.Distribuidor;

import org.example.Dominio.Viandas.Vianda;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Distr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.Dominio.Heladeras.EstadoHeladera.ACTIVA;
import static org.example.Dominio.Suscripciones.TipoSuscripcion.RESTANTES;

public class HeladeraTest {

    @Test
    void testReportarIncidente() {
        Colaborador colaborador = new Colaborador();
        PuntoEstrategico punto = new PuntoEstrategico("zona1", 10.0, 120.0, "Av Diaz 12");
        Heladera heladera = new Heladera(10,0,punto);

        colaborador.agregarFalla(heladera);

        IncidenteFactory inc = new IncidenteFactory();
        inc.crearFalla(colaborador,heladera);

        Assertions.assertNotEquals(heladera.estado, ACTIVA);
    }

    @Test
    void testAbrirConAutorizacion() {
        PuntoEstrategico punto = new PuntoEstrategico("zona1", 10.0, 120.0, "Av Diaz 12");
        Heladera heladera = new Heladera(10, 0, punto);
        Distribuidor distribuidor = new Distribuidor();

        TarjetaDistribuidor tarj = new TarjetaDistribuidor();
        tarj.solicitarAcceso();
        tarj.abrirHeladera(heladera);


        // Verificación
        Assertions.assertEquals(distribuidor.aperturas.size(),1);
    }
}
