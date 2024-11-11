package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.repositorios.RepositorioUsuarios;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

        EntityManager em = BDUtils.getEntityManager();
        String sql = "SELECT p.tipo_persona FROM persona p INNER JOIN usuario u ON u.id=p.usuario_id WHERE u.usuario = :usu";
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("usu", usuarioNombre);

        String tipo_persona = (String) nativeQuery.getSingleResult();


        context.render("/templates/perfil_persona_juridica.mustache", model);
        context.sessionAttribute("tipo_persona", tipo_persona);

        System.out.println("Sesion iniciada");
        context.sessionAttribute("username", usuarioNombre);
        context.sessionAttribute("succesLogin", true);
        context.redirect("/perfil_"+tipo_persona);
      } else {
        System.out.println("ERROR");
        model.put("errorMessage", "El nombre de usuario o la contraseña no coincide.");
        context.render("/templates/login.mustache", model);
      }
    }
  }
}
