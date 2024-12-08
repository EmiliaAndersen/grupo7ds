package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Migrador.Migrador;
import org.example.repositorios.RepositorioColaboradores;
import org.example.repositorios.RepositorioUsuarios;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostUploadCSV implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    Map<String, Object> model = new HashMap<>();
    UploadedFile uploadedFile = context.uploadedFile("file");
    String tipoPersona = context.sessionAttribute("tipo_persona");
    model.put("tipoPersona",tipoPersona);
    RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getRepositorioUsuarios();
    RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstance();


    try{
      // Guardar el archivo temporalmente en el servidor
      Path tempFile = Files.createTempFile("uploaded", ".csv");
      try (var inputStream = uploadedFile.content()) {
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
      }



      // Procesar el archivo con el Migrador
      Migrador migrador = new Migrador();
      List<PersonaHumana> personas = repositorioUsuarios.getPersonasHumanas();
      List<Colaborador> colaboradores = repositorioColaboradores.getColaboradoresHumanos() ;
      migrador.Migrar(tempFile.toString(), personas, colaboradores);
      model.put("successMessage", "Archivo procesado correctamente");
      context.render("/templates/uploadCSV.mustache",model);
    }catch (Exception e){
      e.printStackTrace();
      model.put("errorMessage", "Error al procesar el archivo");
      context.render("/templates/uploadCSV.mustache",model);
    }

  }
}
