package org.example.Handlers;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PostReportes implements @NotNull Handler {
     public void handle(@NotNull Context context) throws IOException {
          String nombreReporte = context.formParam("nombre");

          Path filePath = Paths.get(nombreReporte);
          if(Files.exists(filePath)) {
               context.contentType("text/csv").result(Files.newInputStream(filePath));
          } else {
               context.status(404).result("Reporte no encontrado");
          }
     }
}
