import org.example.Dominio.Colaboraciones.*;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Viandas.Vianda;
import org.example.Servicio.LocalizacionEstrategicaAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class ColaboracionTest {

    private final LocalizacionEstrategicaAPI localizacionEstrategicaAPI = new LocalizacionEstrategicaAPI();
    private final LocalizacionEstrategicaAPI mockLocalizacionEstrategicaAPI =  Mockito.mock(LocalizacionEstrategicaAPI.class);
    @BeforeEach
    public void setUp() {

    }

    @Test
    public void colaboracion_calcularPuntos_DonacionDeDinero() {
        DonacionDeDinero donacionDeDinero = new DonacionDeDinero(LocalDate.now(), 500.0, LocalDate.now());

        Assertions.assertEquals(250.0, donacionDeDinero.calcularPuntos());
    }

//    @Test
//    public void colaboracion_calcularPuntos_DonacionDeVianda() {
//        Vianda vianda1 = new Vianda(null, null, null, null, 0, 0, null);
//        Vianda vianda2 = new Vianda(null, null, null, null, 0, 0, null);
//
//        List<Vianda> listaViandas = new ArrayList<Vianda>();
//        listaViandas.add(vianda1);
//        listaViandas.add(vianda2);
//
//        DonacionDeVianda donacionDeVianda = new DonacionDeVianda(listaViandas);
//        donacionDeVianda.setViandas(listaViandas);
//
//        Assertions.assertEquals(2.0 * 1.5, donacionDeVianda.calcularPuntos());
//    }

    @Test
    public void colaboracion_calcularPuntos_hacerseCargoDeHeladera() {
        LocalDate fechaInicioEjemplo = LocalDate.of(2000, 7, 10);
        PuntoEstrategico ubi1 = new PuntoEstrategico("Ubi1",-34.598300,-58.420570,"Medrano 951");



        HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera(localizacionEstrategicaAPI,ubi1, 2.0);
        hacerseCargoDeHeladera.setFechaInicio(fechaInicioEjemplo);

        Assertions.assertEquals(24*12*5 + 10, hacerseCargoDeHeladera.calcularPuntos());
    }

    @Test
    public void colaboracion_calcularPuntos_distribucionDeViandas() {
        PuntoEstrategico ubi1 = new PuntoEstrategico("Ubi1",-34.598300,-58.420570,"Medrano 951");
        PuntoEstrategico ubi2 = new PuntoEstrategico("Ubi2",-34.598300,-58.420570,"Medrano 951");
        Heladera heladeraOrigen = new Heladera(22.0F, 5.0F, ubi1,100);
        Heladera heladeraDestino = new Heladera(22.0F, 5.0F, ubi2,100);

        DistribucionDeViandas distribucionDeViandas = new DistribucionDeViandas(heladeraOrigen, heladeraDestino, 5.0,"Test", LocalDate.now());

        Assertions.assertEquals(1.0, distribucionDeViandas.calcularPuntos());
    }

    @Test
    public void colaboracion_calcularPuntos_RegistrarPersonasEnSituacionVulnerable(){
        Colaborador colaboradorHumano = new Colaborador();
        RegistrarPersonasEnSituacionVulnerable registrarPersonasEnSituacionVulnerable = new RegistrarPersonasEnSituacionVulnerable();
        //registrarPersonasEnSituacionVulnerable.setColaborador(colaboradorHumano);

        Assertions.assertEquals(2.0, registrarPersonasEnSituacionVulnerable.calcularPuntos());
    }

    @Test
    public void colaboraciones_calcularPuntos_colaboradorConVariasColaboraciones() {
        Colaborador colaboradorHumano = new Colaborador();

        DonacionDeDinero donacionDeDinero1 = new DonacionDeDinero(LocalDate.now(), 500.0, LocalDate.now());
        DonacionDeDinero donacionDeDinero2 = new DonacionDeDinero(LocalDate.now(), 500.0, LocalDate.now());
        RegistrarPersonasEnSituacionVulnerable registrarPersonasEnSituacionVulnerable = new RegistrarPersonasEnSituacionVulnerable();
        //registrarPersonasEnSituacionVulnerable.setColaborador(colaboradorHumano);

        colaboradorHumano.agregarColaboracion(donacionDeDinero1);
        colaboradorHumano.agregarColaboracion(donacionDeDinero2);
        colaboradorHumano.agregarColaboracion(registrarPersonasEnSituacionVulnerable);

        Assertions.assertEquals(502.0, colaboradorHumano.calcularPuntos());
    }

    @Test
    public void colaboraciones_hacerse_cargo_heladera_api_mockito(){
        PuntoEstrategico ubi1 = new PuntoEstrategico("Ubi1",-34.598300,-58.420570,"Medrano 951");
        HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera(mockLocalizacionEstrategicaAPI,ubi1,2.0);



        Mockito.when(mockLocalizacionEstrategicaAPI.getPuntoEstrategico(any(),any())).thenReturn(getPuntosTest());

        List<PuntoEstrategico> puntos = new ArrayList<>();
        puntos = hacerseCargoDeHeladera.getLocalizacionEstrategica();

        List<PuntoEstrategico> puntos2 = new ArrayList<>();
        puntos2 = getPuntosTest();

        // NO SE COMO COMPARAR DOS LISTAS
        Assertions.assertEquals(puntos2.get(0).getLatitud(), puntos.get(0).getLatitud());
        Assertions.assertEquals(puntos2.get(0).getLongitud(), puntos.get(0).getLongitud());

    }
    private List<PuntoEstrategico> getPuntosTest(){
        List<PuntoEstrategico> puntos = new ArrayList<>();

        PuntoEstrategico puntoEstrategico1 = new PuntoEstrategico(
            "PUNTO 1",
            -58.420570,
            -34.598300,
            "Medrano 951"
        );

        PuntoEstrategico puntoEstrategico2 = new PuntoEstrategico(
            "PUNTO 2",
            -59.420570,
            -35.598300,
            "Medrano 951"
        );


        puntos.add(puntoEstrategico1);
        puntos.add(puntoEstrategico2);


        return puntos;
    }
}
