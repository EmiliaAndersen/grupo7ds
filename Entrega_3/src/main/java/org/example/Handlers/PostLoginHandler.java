package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import org.example.BDUtils;
import org.example.Dominio.Rol.Tecnico;
import org.example.Servicio.MetricsService;
import org.example.repositorios.RepositorioTecnicos;
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

    if (usuarioNombre.equals("admin")){
      context.sessionAttribute("username",usuarioNombre);
      context.sessionAttribute("rol","admin");
      context.sessionAttribute("succesLogin", true);
      context.redirect("/backoffice");
      return;
    }
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
        MetricsService.incrementRequestCounter();
        context.sessionAttribute("rol","noAdmin");
        if (esTecnico(usuarioNombre)){
          context.redirect("/backoffice/incidentes");
          return;
        }
        context.redirect("/front_page");
      } else {
        System.out.println("ERROR");
        model.put("errorMessage", "El nombre de usuario o la contraseña no coincide.");
        context.render("/templates/login.mustache", model);
      }
    }
  }

  private boolean esTecnico(String usuarioNombre) {
    RepositorioTecnicos repositorioTecnicos = RepositorioTecnicos.getInstance();

    Tecnico tecnico = repositorioTecnicos.obtenerTecnicoXUsuario(usuarioNombre);
    return (tecnico !=null);
  }
}

