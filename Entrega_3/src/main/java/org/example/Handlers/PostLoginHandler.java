package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Validador.Usuario;
import org.example.repositorios.RepositorioUsuarios;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PostLoginHandler implements Handler {

  @Override
  public void handle(@NotNull Context context) {

    RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getRepositorioUsuarios();

    String usuarioNombre = context.formParam("username");
    String usuarioContraseña = context.formParam("password");

    Map<String, Object> model = new HashMap<>();

    if (usuarioNombre == null || usuarioNombre.isEmpty() || usuarioContraseña == null || usuarioContraseña.isEmpty()) {
      model.put("errorMessage", "El nombre de usuario o la contraseña no pueden estar vacíos.");
      context.render("/templates/login.mustache", model);
    } else {
      if (repositorioUsuarios.verificarUsuarioYcontrasena(usuarioNombre,usuarioContraseña)){

        System.out.println("Sesion iniciada");
        context.sessionAttribute("username", usuarioNombre);
        context.sessionAttribute("succesLogin", true);
        context.redirect("/perfil");
      } else {
        System.out.println("ERROR");
        model.put("errorMessage", "El nombre de usuario o la contraseña no coincide.");
        context.render("/templates/login.mustache", model);
      }
    }
  }
}
