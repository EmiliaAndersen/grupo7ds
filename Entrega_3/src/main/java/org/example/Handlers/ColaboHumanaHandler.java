package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Heladeras.Heladera;
import org.example.repositorios.RepositorioHeladeras;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class ColaboHumanaHandler implements Handler {

    public void handle(@NotNull Context ctx) {
        var model = new HashMap<String, Object>();
        RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();

        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasHeladeras();

        model.put("heladeras",heladeras);
        model.put("tipoPersona", ctx.sessionAttribute("tipo_persona"));
        ctx.render("/templates/colaboracionHumana.mustache", model);
    }
}
