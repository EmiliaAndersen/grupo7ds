package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.repositorios.RepositorioNotificaciones;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GetPersVulnHandler implements @NotNull Handler {
     
      public void handle(@NotNull Context context){
            var model = new HashMap<String, Object>();
            RepositorioNotificaciones repositorioNotificaciones = RepositorioNotificaciones.getInstance();
            String usuarioAVerificar = context.sessionAttribute("username");
            if (repositorioNotificaciones.obtenerNotificacionesActivasXUsuario(usuarioAVerificar).isEmpty()){
                  model.put("notificacionActiva","<i class=\"bi bi-bell\"></i>");
            }else {
                  model.put("notificacionActiva","<i class=\"bi bi-bell-fill\"></i>");
            }

            model.put("tipoPersona", context.sessionAttribute("tipo_persona"));

            Boolean success = context.sessionAttribute("successPost");
            Boolean error = context.sessionAttribute("errorPost");
            if (  success != null) {
                  model.put("successMessage", "Persona registrada correctamente");
                  context.sessionAttribute("successPost", null);
            }
            if (  error != null) {
                  model.put("errorMessage", "Error al registar la persona, intentelo devuelta");
                  context.sessionAttribute("errorPost", null);
            }
            context.render("/templates/personaVulnerable.mustache", model);
  }
}
