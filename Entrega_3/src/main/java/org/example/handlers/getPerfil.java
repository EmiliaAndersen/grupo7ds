package org.example.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class getPerfil implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();

    if (context.sessionAttribute("succesLogin")) {
      model.put( "successMessage", "Session iniciada correctamente " + context.sessionAttribute("username"));
      context.sessionAttribute("successLogin", null);
    }
    context.render("/templates/perfil.mustache", model);
  }
}
