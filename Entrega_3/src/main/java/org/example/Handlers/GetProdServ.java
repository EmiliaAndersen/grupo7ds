package org.example.Handlers;

import java.util.HashMap;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class GetProdServ implements @NotNull Handler {
          public void handle(@NotNull Context context){
        var model = new HashMap<String, Object>();
        context.render("/templates/prodserv.mustache", model);
  }
}
