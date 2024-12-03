package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class getBackoffice implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();
    model.put("tipoPersona", context.sessionAttribute("tipo_persona"));
    context.render("templates/backofficeHeladeras.mustache");
  }
}
