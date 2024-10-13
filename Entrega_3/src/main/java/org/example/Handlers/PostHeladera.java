package org.example.Handlers;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PostHeladera implements @NotNull Handler {
          public void handle(@NotNull Context context){
            context.redirect("/perfil");
  }
}
