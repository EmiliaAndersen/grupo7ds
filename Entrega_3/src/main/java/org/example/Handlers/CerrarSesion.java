package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CerrarSesion implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();

    context.sessionAttribute("username", null);
    context.redirect("/login");
  }
}