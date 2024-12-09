package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class getBackofficeTecnicos implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {

    context.render("/templates/backofficeTecnicos.mustache");
  }
}
