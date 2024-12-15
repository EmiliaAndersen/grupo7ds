import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.*;
import org.example.Dominio.Reportes.ReporteFallasPorHeladera;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.Tecnico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncidentesTest {

    IncidenteFactory factory;
    Heladera heladera;
    ReporteFallasPorHeladera reporteFallas;

    @BeforeEach
    void setUp() {
        factory = new IncidenteFactory();
        heladera = new Heladera(0, 0, null, 0);
        //reporteFallas = ReporteFallasPorHeladera.getInstance();
    }

    @Test
    public void instanciarAlerta() {
        Incidente alerta = factory.crearAlerta(TipoAlerta.TEMPERATURA, heladera);

        Assertions.assertInstanceOf(Alerta.class, alerta);
    }

    @Test
    public void instanciarFallaTecnica() {
        Incidente fallaTecnica = factory.crearFalla(new Colaborador(), heladera, "Error"); //ver

        Assertions.assertInstanceOf(FallaTecnica.class, fallaTecnica);
    }

    @Test
    public void notificarReporte() {
        factory.agregarSuscriptor(reporteFallas);
        factory.crearAlerta(TipoAlerta.TEMPERATURA, heladera);

       // Assertions.assertEquals(reporteFallas.cantidadDeFallas, 1);
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
