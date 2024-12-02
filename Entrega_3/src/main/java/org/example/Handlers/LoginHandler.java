package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LoginHandler implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();
    Boolean successSignIn = context.sessionAttribute("successSignIn");
    String usuarioNombre = context.sessionAttribute("username");

    if ( successSignIn != null) {
      model.put("successMessage", "Usuario creado correctamente");
      context.sessionAttribute("successSignIn", null);
    }


    context.render("/templates/login.mustache", model);
  }

}
