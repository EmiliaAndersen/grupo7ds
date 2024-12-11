package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class getBackofficeHeladeras implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();
    Boolean success = context.sessionAttribute("successMessage");
    Boolean error = context.sessionAttribute("errorMessage");
    if (  success != null) {
      model.put("successMessage", "Heladera creada correctamente");
      context.sessionAttribute("successMessage", null);
    }
    if ( error != null) {
      model.put("errorMessage", "Error al crear la heladera, intentelo devuelta");
      context.sessionAttribute("errorMessage", null);
    }
    context.render("/templates/backofficeHeladeras.mustache",model);
  }
}
