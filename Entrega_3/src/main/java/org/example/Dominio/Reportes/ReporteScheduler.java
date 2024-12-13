package org.example.Dominio.Reportes;

import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReporteScheduler {

    private final List<GeneradorDeReportes> generadores;

    public ReporteScheduler(List<GeneradorDeReportes> generadores) {
        this.generadores = generadores;
    }

    public void iniciarScheduler(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ejecutarReportes();
            }
        }, 0, 7 * 24 * 60 * 60 * 1000);
    }

    @SneakyThrows
    public void ejecutarReportes() {
        generadores.forEach(generador -> {
        try {
                    generador.generarReporte();
        } catch (IOException e){
            System.err.println("Error al generar un reporte: "+e.getMessage());
            e.printStackTrace();
        }});
    }
}
