package org.example.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class LoginHandler implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();
    context.render("/templates/login.mustache", model);
  }
}
