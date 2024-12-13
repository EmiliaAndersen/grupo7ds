package org.example.Dominio.Reportes;

import org.example.Dominio.Incidentes.Incidente;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Map<Long, Long> fallasTotales = incidentes.stream()
                .collect(Collectors.groupingBy(incidente -> incidente.getHeladera().getId(),
                        Collectors.counting()));

        Map<Long, Long> fallasAnteriores = cargarUltimoReporte("fallas_por_heladera.csv");

        StringBuilder csvBuilder = new StringBuilder("\"HeladeraID\",\"Fallas Totales\",\"Fallas Semana\"\n");
        fallasTotales.forEach((heladeraId, cantidad) -> {
            long fallasSemana = cantidad - fallasAnteriores.getOrDefault(heladeraId, 0L);
            csvBuilder.append("\"").append(heladeraId).append("\"").append(",")
                    .append("\"").append(cantidad).append("\"").append(",")
                    .append("\"").append(fallasSemana).append("\"").append("\n");
        });

        fileservice.guardarReporte("fallas_por_heladera.csv", csvBuilder.toString());
    }

    private Map<Long, Long> cargarUltimoReporte(String file) throws IOException {
        Map<Long, Long> datos = new HashMap<>();
        Path filePath = Paths.get(file);

        if(!Files.exists(filePath)){
            return datos;
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)){
            br.readLine();
            String linea;
            while((linea = br.readLine()) != null){
                String[] partes = linea.replaceAll("\"", "").split(",");
                long heladeraId = Long.parseLong(partes[0]);
                long fallasTotales = Long.parseLong(partes[1]);
                datos.put(heladeraId, fallasTotales);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return datos;
    }
}
