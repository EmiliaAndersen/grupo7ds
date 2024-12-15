import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.PersonaVulnerable;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Tarjetas.TarjetaVulnerable;
import org.example.Dominio.Viandas.Vianda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TarjetaVulnerableTest {

    PersonaVulnerable personaVulnerable;
    Heladera heladera;
    TarjetaVulnerable tarjetaVulnerable;

    @BeforeEach
    public void setUp() {
        personaVulnerable = new PersonaVulnerable();
        tarjetaVulnerable = new TarjetaVulnerable(personaVulnerable);
        PuntoEstrategico ubicacion = new PuntoEstrategico();
        Vianda vianda1 = new Vianda(null, null, null, null, 0, 0, null,null);
        Vianda vianda2 = new Vianda(null, null, null, null, 0, 0, null,null);
        Vianda vianda3 = new Vianda(null, null, null, null, 0, 0, null,null);
        Vianda vianda4 = new Vianda(null, null, null, null, 0, 0, null,null);
        Vianda vianda5 = new Vianda(null, null, null, null, 0, 0, null,null);
        Vianda vianda6 = new Vianda(null, null, null, null, 0, 0, null,null);
        Vianda vianda7 = new Vianda(null, null, null, null, 0, 0, null,null);
        Vianda vianda8 = new Vianda(null, null, null, null, 0, 0, null,null);
        heladera = new Heladera(22.0F, 5.0F, ubicacion,100);
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
        tarjetaVulnerable.registrarUso(heladera);

        Assertions.assertEquals(1, tarjetaVulnerable.getUsos().size());
    }

    @Test
    public void tarjeta_LimitesDiariosAlcanzados() {
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);

        // El 5to no tendria que pasar
        Assertions.assertEquals(4, tarjetaVulnerable.getUsos().size());
    }

    @Test
    public void tarjeta_LimitesDiariosMayoresA4PorMenorACargo(){
        personaVulnerable.setMenoresACargo(1);

        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);
        tarjetaVulnerable.registrarUso(heladera);

        Assertions.assertEquals(6, tarjetaVulnerable.getUsos().size());
    }
}
