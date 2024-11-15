import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.*;
import org.example.Dominio.Reportes.GeneradorReporteFallas;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.Tecnico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class IncidentesTest {

    IncidenteFactory factory;
    Heladera heladera;
    GeneradorReporteFallas reporteFallas;

    @BeforeEach
    void setUp() {
        factory = new IncidenteFactory();
        heladera = new Heladera(0, 0, null);
        reporteFallas = GeneradorReporteFallas.getInstance();
    }

    @Test
    public void instanciarAlerta() {
        Incidente alerta = factory.crearAlerta(TipoAlerta.TEMPERATURA, heladera);

        Assertions.assertInstanceOf(Alerta.class, alerta);
    }

    @Test
    public void instanciarFallaTecnica() {
        Incidente fallaTecnica = factory.crearFalla(new Colaborador(), heladera);

        Assertions.assertInstanceOf(FallaTecnica.class, fallaTecnica);
    }

    @Test
    public void notificarReporte() {
        factory.agregarSuscriptor(reporteFallas);
        factory.crearAlerta(TipoAlerta.TEMPERATURA, heladera);

        Assertions.assertEquals(reporteFallas.cantidadDeFallas, 1);
    }

    @Test
    public void visitaIncidente(){
        Incidente alerta = factory.crearAlerta(TipoAlerta.TEMPERATURA, heladera);

        Visita visita = new Visita(new Tecnico(), null);
        visita.setPudoResolver(true);

        alerta.registrarVisita(visita);

        Assertions.assertEquals(alerta.getVisitas().size(), 1);
    }
}
