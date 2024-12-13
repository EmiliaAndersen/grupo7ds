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
        Map<Long, Long> viandasPorColaboradorTotal = viandas.stream()
                .collect(Collectors.groupingBy(vianda -> vianda.getColaborador().getId(),
                        Collectors.counting()));

        Map<Long, Long> viandasPorColaboradorAnterior = cargarUltimoReporte("viandas_por_colaborador.csv");

        StringBuilder csvBuilder = new StringBuilder("\"ColaboradorID\",\"Viandas Totales\",\"Viandas Semana\"\n");
        viandasPorColaboradorTotal.forEach((colaboradorId, cantidad) -> {
           long viandasSemana = cantidad - viandasPorColaboradorAnterior.getOrDefault(colaboradorId, 0L);
           csvBuilder.append("\"").append(colaboradorId).append("\"").append(",")
                   .append("\"").append(cantidad).append("\"").append(",")
                   .append("\"").append(viandasSemana).append("\"").append("\n");
        });

        fileService.guardarReporte("viandas_por_colaborador.csv", csvBuilder.toString());
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
                String[] valores = linea.replaceAll("\"","").split(",");
                long colaboradorId = Long.parseLong(valores[0]);
                long cantidadViandas = Long.parseLong(valores[1]);
                datos.put(colaboradorId, cantidadViandas);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return datos;
    }
}
