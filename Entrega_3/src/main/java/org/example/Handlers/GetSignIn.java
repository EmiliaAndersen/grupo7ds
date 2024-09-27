package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GetSignIn implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();
    context.render("/templates/signin.mustache", model);
  }

}
