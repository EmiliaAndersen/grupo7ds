package org.example.Dominio.Reportes;


import org.example.Dominio.Viandas.Vianda;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteViandasPorHeladera implements GeneradorDeReportes{

    private final DataService dataService;
    private final FileService fileService;

    public ReporteViandasPorHeladera(DataService dataService, FileService fileService) {
        this.dataService = dataService;
        this.fileService = fileService;
    }

    @Override
    public void generarReporte() throws IOException {
        List<Vianda> viandas = dataService.obtenerViandas();
        Map<Long, Long> viandasPorHeladera = viandas.stream()
                .collect(Collectors.groupingBy(vianda -> vianda.getHeladera().getId(),
                        Collectors.counting()));

        StringBuilder csvBuilder = new StringBuilder("\"HeladeraID\",\"Viandas\"\n");
        viandasPorHeladera.forEach((heladeraId, cantidad) ->
                csvBuilder.append("\"").append(heladeraId).append("\"").append(",")
                        .append("\"").append(cantidad).append("\"").append("\n")
        );

        fileService.guardarReporte("viandas_por_heladera.csv", csvBuilder.toString());
    }
}
