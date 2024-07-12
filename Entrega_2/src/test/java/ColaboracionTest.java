import org.example.Dominio.Colaboraciones.*;
import org.example.Dominio.Colaboradores.PersonaHumana;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Viandas.Vianda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ColaboracionTest {

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void colaboracion_calcularPuntos_DonacionDeDinero() {
        DonacionDeDinero donacionDeDinero = new DonacionDeDinero(LocalDate.now(), 500.0, LocalDate.now());

        Assertions.assertEquals(250.0, donacionDeDinero.calcularPuntos());
    }

/*    @Test
    public void colaboracion_calcularPuntos_DonacionDeVianda() {
        Vianda vianda1 = new Vianda();
        Vianda vianda2 = new Vianda();
        
        List<Vianda> listaViandas = new ArrayList<Vianda>();
        listaViandas.add(vianda1);
        listaViandas.add(vianda2);
        
        DonacionDeVianda donacionDeVianda = new DonacionDeVianda(listaViandas);

        Assertions.assertEquals(2.0 * 1.5, donacionDeVianda.calcularPuntos());
    }

    @Test
    public void colaboracion_calcularPuntos_hacerseCargoDeHeladera() {
        LocalDate fechaInicioEjemplo = LocalDate.of(2000, 6, 10);

        HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera(fechaInicioEjemplo);

        Assertions.assertEquals(24*12*5, hacerseCargoDeHeladera.calcularPuntos());
    }

    @Test
    public void colaboracion_calcularPuntos_distribucionDeViandas() {
        Heladera heladeraOrigen = new Heladera();
        Heladera heladeraDestino = new Heladera();

        DistribucionDeViandas distribucionDeViandas = new DistribucionDeViandas(heladeraOrigen,
                                                                                heladeraDestino,
                                                                                "Test",
                                                                                LocalDate.now());

        Assertions.assertEquals(1.0, distribucionDeViandas.calcularPuntos());
    }

    @Test
    public void colaboracion_calcularPuntos_RegistrarPersonasEnSituacionVulnerable(){
        PersonaHumana colaboradorHumano = new PersonaHumana();
        RegistrarPersonasEnSituacionVulnerable registrarPersonasEnSituacionVulnerable = new RegistrarPersonasEnSituacionVulnerable(colaboradorHumano);

        Assertions.assertEquals(2.0, registrarPersonasEnSituacionVulnerable.calcularPuntos());
    }

    @Test
    public void colaboraciones_calcularPuntos_personaHumanaConVariasColaboraciones() {
        PersonaHumana colaboradorHumano = new PersonaHumana();

        DonacionDeDinero donacionDeDinero1 = new DonacionDeDinero(LocalDate.now(), 500.0, LocalDate.now());
        DonacionDeDinero donacionDeDinero2 = new DonacionDeDinero(LocalDate.now(), 500.0, LocalDate.now());
        RegistrarPersonasEnSituacionVulnerable registrarPersonasEnSituacionVulnerable = new RegistrarPersonasEnSituacionVulnerable(colaboradorHumano);

        colaboradorHumano.agregarColaboracion(donacionDeDinero1);
        colaboradorHumano.agregarColaboracion(donacionDeDinero2);
        colaboradorHumano.agregarColaboracion(registrarPersonasEnSituacionVulnerable);

        Assertions.assertEquals(502.0, colaboradorHumano.calcularPuntos());
    }

 */
}
