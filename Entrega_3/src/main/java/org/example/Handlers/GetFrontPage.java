package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GetFrontPage implements Handler{
  @Override
  public void handle(@NotNull Context context) throws Exception {
    Map<String, Object> model = new HashMap<>();
    String tipoPersona = context.sessionAttribute("tipo_persona");
    model.put("tipoPersona",tipoPersona);

    context.render("templates/front_page.mustache",model);

  }
}
