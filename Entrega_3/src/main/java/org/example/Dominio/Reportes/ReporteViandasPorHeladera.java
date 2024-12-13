package org.example.Dominio.Reportes;


import org.example.Dominio.Viandas.Vianda;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
        Map<Long, Long> viandasPorHeladeraTotal = viandas.stream()
                .collect(Collectors.groupingBy(vianda -> vianda.getHeladera().getId(),
                        Collectors.counting()));

        Map<Long, Long> viandasPorHeladeraAnterior = cargarUltimoReporte("viandas_por_heladera.csv");

        StringBuilder csvBuilder = new StringBuilder("\"HeladeraID\",\"Viandas Total\",\"Viandas Semana\"\n");
        viandasPorHeladeraTotal.forEach((heladeraId, cantidad) -> {
            long cantidadSemana = cantidad - viandasPorHeladeraAnterior.getOrDefault(heladeraId, 0L);
            csvBuilder.append("\"").append(heladeraId).append("\"").append(",")
                    .append("\"").append(cantidad).append("\"").append(",")
                    .append("\"").append(cantidadSemana).append("\"").append("\n");
        });

        fileService.guardarReporte("viandas_por_heladera.csv", csvBuilder.toString());
    }

    private Map<Long, Long> cargarUltimoReporte(String file) throws IOException {
        Map<Long, Long> viandasPorHeladeraTotal = new HashMap<>();
        Path filePath = Paths.get(file);

        if(!Files.exists(filePath)){
            return viandasPorHeladeraTotal;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)){
            br.readLine();
            String linea;
            while((linea = br.readLine()) != null){
                String[] partes = linea.replaceAll("\"","").split(",");
                long heladeraID = Long.parseLong(partes[0]);
                long cantidad = Long.parseLong(partes[1]);
                viandasPorHeladeraTotal.put(heladeraID, cantidad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return viandasPorHeladeraTotal;
    }
}
