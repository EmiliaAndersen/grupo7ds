package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CargaColaboHandler implements Handler {

    public void handle(@NotNull Context ctx) {
        var model = new HashMap<String, Object>();
        model.put("tipoPersona", ctx.sessionAttribute("tipo_persona"));
        ctx.render("/templates/cargaColaboraciones.mustache", model);
    }
}
