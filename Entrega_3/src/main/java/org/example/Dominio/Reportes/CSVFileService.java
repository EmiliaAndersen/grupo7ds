package org.example.Dominio.Reportes;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileService implements FileService {

    @Override
    public void guardarReporte(String nombreArchivo, String contenido) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))){
            bw.write(contenido);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error al guardar el reporte: "+nombreArchivo);
        }
    }
}
