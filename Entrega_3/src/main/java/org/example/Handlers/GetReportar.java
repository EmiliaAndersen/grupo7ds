package org.example.Handlers;
import java.util.HashMap;
import java.util.List;

import org.example.Dominio.Heladeras.Heladera;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioNotificaciones;
import org.jetbrains.annotations.NotNull;
import io.javalin.http.Context;
import io.javalin.http.Handler;


public class GetReportar implements @NotNull Handler {
    
      public void handle(@NotNull Context context){
        var model = new HashMap<String, Object>();
        RepositorioNotificaciones repositorioNotificaciones = RepositorioNotificaciones.getInstance();
        String usuarioAVerificar = context.sessionAttribute("username");
        if (repositorioNotificaciones.obtenerNotificacionesActivasXUsuario(usuarioAVerificar).isEmpty()){
          model.put("notificacionActiva","<i class=\"bi bi-bell\"></i>");
        }else {
          model.put("notificacionActiva","<i class=\"bi bi-bell-fill\"></i>");
        }
        RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();
        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasHeladeras();
        model.put("heladeras",heladeras);


        boolean errorReportar = false;

      if(context.sessionAttribute("errorReportar ") != null){
      errorReportar = context.sessionAttribute("errorReportar ");
      if(errorReportar ){
        model.put("errorMessageReportar ","Ocurrio un error");
        context.sessionAttribute("errorReportar ",false);
      }

      }

      boolean successReportar = false;
      if(context.sessionAttribute("successReportar") != null){
          successReportar = context.sessionAttribute("successReportar");
          if(successReportar){
            model.put("successReportar","Se reporto correctamente");
            context.sessionAttribute("successReportar",false);
          }
      }

      model.put("tipoPersona", context.sessionAttribute("tipo_persona"));
    context.render("/templates/reportar.mustache", model);
  }
}
