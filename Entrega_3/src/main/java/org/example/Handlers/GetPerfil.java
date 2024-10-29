package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GetPerfil implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();
    Boolean succesLogin = context.sessionAttribute("succesLogin");

    if ( succesLogin != null && succesLogin) {
      model.put("successMessage", "Session iniciada correctamente " + context.sessionAttribute("username"));
      context.sessionAttribute("succesLogin", null);
    }
    context.render("/templates/perfil.mustache", model);
  }
}
