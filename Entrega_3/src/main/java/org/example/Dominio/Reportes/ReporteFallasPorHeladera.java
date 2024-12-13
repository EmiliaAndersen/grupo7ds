package org.example.Dominio.Reportes;

import org.example.Dominio.Incidentes.Incidente;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteFallasPorHeladera implements GeneradorDeReportes {

    private final DataService dataService;
    private final FileService fileservice;

    public ReporteFallasPorHeladera(DataService dateService, FileService fileService) {
        this.dataService = dateService;
        this.fileservice = fileService;
    }

    @Override
    public void generarReporte() throws IOException {
        List<Incidente> incidentes = dataService.obtenerIncidentes();
        Map<Long, Long> fallasPorHeladera = incidentes.stream()
                .collect(Collectors.groupingBy(incidente -> incidente.getHeladera().getId(),
                        Collectors.counting()));

        StringBuilder csvBuilder = new StringBuilder("\"HeladeraID\",\"Fallas\"\n");
        fallasPorHeladera.forEach((heladeraId, cantidad) ->
                csvBuilder.append("\"").append(heladeraId).append("\"").append(",")
                        .append("\"").append(cantidad).append("\"").append("\n")
        );

        fileservice.guardarReporte("fallas_por_heladera.csv", csvBuilder.toString());
    }
}
