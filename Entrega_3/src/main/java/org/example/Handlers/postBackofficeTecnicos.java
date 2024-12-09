package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class postBackofficeTecnicos implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    context.formParam("radio");
    context.formParam("radio");
    context.formParam("radio");
    context.formParam("radio");

  }
}
