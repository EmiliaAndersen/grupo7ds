package org.example.Dominio.Reportes;

import org.example.Dominio.Viandas.Vianda;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteViandasPorColaborador implements GeneradorDeReportes{

    private final DataService dataService;
    private final FileService fileService;

    public ReporteViandasPorColaborador(DataService dataService, FileService fileService) {
        this.dataService = dataService;
        this.fileService = fileService;
    }

    @Override
    public void generarReporte() throws IOException {
        List<Vianda> viandas = dataService.obtenerViandas();
        Map<Long, Long> viandasPorColaborador = viandas.stream()
                .collect(Collectors.groupingBy(vianda -> vianda.getColaborador().getId(),
                        Collectors.counting()));

        StringBuilder csvBuilder = new StringBuilder("\"ColaboradorID\",\"Viandas\"\n");
        viandasPorColaborador.forEach((colaboradorId, cantidad) -> {
           csvBuilder.append("\"").append(colaboradorId).append("\"").append(",")
                   .append("\"").append(cantidad).append("\"").append("\n");
        });

        fileService.guardarReporte("viandas_por_colaborador.csv", csvBuilder.toString());
    }
}
