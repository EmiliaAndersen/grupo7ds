import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Heladeras.sensores.SensorMovimiento;
import org.example.Dominio.Heladeras.sensores.SensorTemperatura;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;

public class SensoresTest {

    Heladera heladera;
    SensorTemperatura sensorTemperatura;
    SensorMovimiento sensorMovimiento;
//    Mockito API;

    @BeforeEach
    public void setUp() {
        PuntoEstrategico unaUbicacion = new PuntoEstrategico("Ubi1",-34.598300,-58.420570,"Medrano 951");
        heladera = new Heladera(22.0F, 5.0F, unaUbicacion,100);

        sensorTemperatura = new SensorTemperatura();
        sensorTemperatura.heladera = heladera;

        sensorMovimiento = new SensorMovimiento();
        sensorMovimiento.heladera = heladera;
//        API = Mockito.mock(Mockito.class);
    }

    @Test
    public void sensorDeTemperatura_seRegistraTemperaturaBaja(){
        float temperaturaRegistrada = 4.0F;
        sensorTemperatura.setUltimaTemperaturaRegistrada(temperaturaRegistrada);

        Assertions.assertEquals(heladera.estado, EstadoHeladera.TEMPERATURA_BAJA);
    }

    @Test
    public void sensorDeTemperatura_seRegistraTemperaturaAlta(){
        float temperaturaRegistrada = 23.0F;
        sensorTemperatura.setUltimaTemperaturaRegistrada(temperaturaRegistrada);

        Assertions.assertEquals(heladera.estado, EstadoHeladera.TEMPERATURA_ALTA);
    }

    @Test
    public void sensorDeTemperatura_seRegistraTemperaturaNormal(){
        float temperaturaRegistrada = 10.0F;
        sensorTemperatura.setUltimaTemperaturaRegistrada(temperaturaRegistrada);

        Assertions.assertEquals(heladera.estado, EstadoHeladera.ACTIVA);
    }

    @Test
    public void sensorDeMovimiento_seDetectaUnMovimiento(){

        Assertions.assertThrows(Exception.class, () -> {sensorMovimiento.enviarAlerta();});
    }
}

