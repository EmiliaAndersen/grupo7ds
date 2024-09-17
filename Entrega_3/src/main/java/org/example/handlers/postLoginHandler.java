package org.example.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Validador.Usuario;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class postLoginHandler implements Handler {

  @Override
  public void handle(@NotNull Context context) {
    String usuarioNombre = context.formParam("username");
    String usuarioContraseña = context.formParam("password");

    Map<String, Object> model = new HashMap<>();

    if (usuarioNombre == null || usuarioNombre.isEmpty() || usuarioContraseña == null || usuarioContraseña.isEmpty()) {
      model.put("errorMessage", "El nombre de usuario o la contraseña no pueden estar vacíos.");
      context.render("/templates/login.mustache", model);
    } else {
      Usuario usuario = new Usuario(usuarioNombre, usuarioContraseña);
      System.out.println("Usuario creado: " + usuario.getUsuario());

      context.sessionAttribute("username", usuario.getUsuario());
      context.sessionAttribute("succesLogin", true);
      context.redirect("/perfil");
    }
  }
}
