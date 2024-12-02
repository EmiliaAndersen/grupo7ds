package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GetSignIn implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();
    model.put("tipoPersona", context.sessionAttribute("tipo_persona"));
    boolean errorSignIn = false;

    if(context.sessionAttribute("ErrorSignIn") != null){
      errorSignIn = context.sessionAttribute("ErrorSignIn");
      if(errorSignIn){
        model.put("errorMessageSignIn","El usuario ya existe");
        context.sessionAttribute("ErrorSignIn",false);
      }

    }

    context.render("/templates/signin.mustache", model);
  }

}
