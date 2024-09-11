package org.example.Dominio.Reportes;

import java.util.List;

public class ReportesController {
    private List<GeneradorDeReportes> generadores;

    public void generarReporte(TipoReporte tipoReporte){
        GeneradorDeReportes generador = this.obtenerGenerador(tipoReporte);
        if(generador == null){
            return;
        }
        double reporte = generador.obtenerReporte();
        System.out.println(reporte);
    }

    public void agregarGenerador(GeneradorDeReportes generador){
        generadores.add(generador);
    }

    private GeneradorDeReportes obtenerGenerador(TipoReporte tipoReporte) {
        for (GeneradorDeReportes generador : this.generadores) {
            if (generador.isTypeOf(tipoReporte)) {
                return generador;
            }
        }
        return null;
    }
}
