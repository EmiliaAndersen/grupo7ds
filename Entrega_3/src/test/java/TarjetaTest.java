import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.PersonaVulnerable;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Tarjetas.Tarjeta;
import org.example.Dominio.Viandas.Vianda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TarjetaTest {

    PersonaVulnerable personaVulnerable;
    Heladera heladera;
    Tarjeta tarjeta;

    @BeforeEach
    public void setUp() {
        personaVulnerable = new PersonaVulnerable();
        tarjeta = new Tarjeta(personaVulnerable);
        PuntoEstrategico ubicacion = new PuntoEstrategico();
        Vianda vianda1 = new Vianda(null, null, null, null, 0, 0, null);
        Vianda vianda2 = new Vianda(null, null, null, null, 0, 0, null);
        Vianda vianda3 = new Vianda(null, null, null, null, 0, 0, null);
        Vianda vianda4 = new Vianda(null, null, null, null, 0, 0, null);
        Vianda vianda5 = new Vianda(null, null, null, null, 0, 0, null);
        Vianda vianda6 = new Vianda(null, null, null, null, 0, 0, null);
        Vianda vianda7 = new Vianda(null, null, null, null, 0, 0, null);
        Vianda vianda8 = new Vianda(null, null, null, null, 0, 0, null);
        heladera = new Heladera(22.0F, 5.0F, ubicacion);
        heladera.agregarVianda(vianda1);
        heladera.agregarVianda(vianda2);
        heladera.agregarVianda(vianda3);
        heladera.agregarVianda(vianda4);
        heladera.agregarVianda(vianda5);
        heladera.agregarVianda(vianda6);
        heladera.agregarVianda(vianda7);
        heladera.agregarVianda(vianda8);
    }

    @Test
    public void tarjeta_personaVulnerableUsaTarjeta() {
        tarjeta.registrarUso(heladera);

        Assertions.assertEquals(1, tarjeta.getUsos().size());
    }

    @Test
    public void tarjeta_LimitesDiariosAlcanzados() {
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);

        // El 5to no tendria que pasar
        Assertions.assertEquals(4, tarjeta.getUsos().size());
    }

    @Test
    public void tarjeta_LimitesDiariosMayoresA4PorMenorACargo(){
        personaVulnerable.setMenoresACargo(1);

        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);
        tarjeta.registrarUso(heladera);

        Assertions.assertEquals(6, tarjeta.getUsos().size());
    }
}
