package org.example.Handlers;
import java.util.HashMap;
import java.util.List;

import org.example.Dominio.Heladeras.Heladera;
import org.example.repositorios.RepositorioHeladeras;
import org.jetbrains.annotations.NotNull;
import io.javalin.http.Context;
import io.javalin.http.Handler;


public class GetReportar implements @NotNull Handler {
    
      public void handle(@NotNull Context context){
        var model = new HashMap<String, Object>();
        RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();
        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasHeladeras();
        model.put("heladeras",heladeras);

        model.put("tipoPersona", context.sessionAttribute("tipo_persona"));
        context.render("/templates/reportar.mustache", model);
  }
}
