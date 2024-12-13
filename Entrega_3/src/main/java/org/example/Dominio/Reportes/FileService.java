package org.example.Dominio.Reportes;

import java.io.IOException;

public interface FileService {
    public void guardarReporte(String nombreArchivo, String contenido) throws IOException;
}
