package org.example.Handlers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.example.Dominio.Reportes.FileService;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class GetReportes implements @NotNull Handler {
     
//      public void handle(@NotNull Context context){
//        var model = new HashMap<String, Object>();
//        model.put("tipoPersona", context.sessionAttribute("tipo_persona"));
//        context.render("/templates/reportes.mustache", model);
//  }

    public GetReportes() {
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Map<String, Object> model = new HashMap<>();
        String tipoPersona = ctx.sessionAttribute("tipo_persona");
        model.put("tipoPersona",tipoPersona);

        ctx.render("templates/reportes.mustache",model);
    }
}
