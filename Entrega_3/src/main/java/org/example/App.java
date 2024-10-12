package org.example;

import io.javalin.Javalin;
import org.example.Handlers.*;

public class App {
  public static void main(String[] args) {

    Javalin app = Javalin.create(javalinConfig -> {
          javalinConfig.staticFiles.add("/");
          javalinConfig.plugins.enableCors(cors -> {
            cors.add(it -> it.anyHost());
          });
        })
        .get("/", ctx -> ctx.result("Server iniciado :)"))
        .start(8085);

    //Utilizo get y el path para crear esa ruta, despues creo un handler para manejarla desde ahi
    //Utilizo posy y el path de esa ruta, despues creo un handler para manejarlo desde ahi
    app.get("/login",  new LoginHandler());
    app.post("/login", new PostLoginHandler());

    app.get("/signin", new GetSignIn());
    app.post("/signin", new PostSignIn());


    app.before("/perfil", new GetPerfil());

    app.get("/colaboracionHumana", new ColaboHumanaHandler());
    app.post("/colaboracionHumana", new PostColaboHumanaHandler());
  }
}
