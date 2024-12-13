package org.example.Dominio.Reportes;

import java.util.List;

public class ReportesController {

    private final FileService fileService;

    public ReportesController(FileService fileService) {
        this.fileService = fileService;
    }

    private List<GeneradorDeReportes> generadores;

    public void generarReporte(TipoReporte tipoReporte){
        GeneradorDeReportes generador = this.obtenerGenerador(tipoReporte);
        if(generador == null){
            return;
        }
        //double reporte = generador.generarReporte();
        //System.out.println(reporte);
    }

    public void agregarGenerador(GeneradorDeReportes generador){
        generadores.add(generador);
    }

    private GeneradorDeReportes obtenerGenerador(TipoReporte tipoReporte) {
//        for (GeneradorDeReportes generador : this.generadores) {
//            if (generador.isTypeOf(tipoReporte)) {
//                return generador;
//            }
//        }
        return null;
    }
}
