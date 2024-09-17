package org.example;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinMustache;
import com.github.mustachejava.DefaultMustacheFactory;
import org.example.handlers.LoginHandler;
import org.example.handlers.getPerfil;
import org.example.handlers.postLoginHandler;

import java.util.HashMap;

public class App {
  public static void main(String[] args) {

    Javalin app = Javalin.create(javalinConfig -> {
          javalinConfig.staticFiles.add("/");
          javalinConfig.plugins.enableCors(cors -> {
            cors.add(it -> it.anyHost());
          });// Asegúrate de que tengas una carpeta 'public' para los archivos estáticos.
        })
        .get("/", ctx -> ctx.result("hola"))
        .start(8085);

    //Utilizo get y el path para crear esa ruta, despues creo un handler para manejarla desde ahi
    app.get("/login",  new LoginHandler());
    app.post("/login", new postLoginHandler());


    app.get("/perfil", new getPerfil());

  }
}
