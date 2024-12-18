package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.DistribucionDeViandas;
import org.example.Dominio.Suscripciones.Notificacion;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.repositorios.RepositorioNotificaciones;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class getNotificaciones implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();
    EntityManager em = BDUtils.getEntityManager();

    RepositorioNotificaciones repositorioNotificaciones = RepositorioNotificaciones.getInstance();
    String usuarioAVerificar = context.sessionAttribute("username");
    if (repositorioNotificaciones.obtenerNotificacionesActivasXUsuario(usuarioAVerificar).isEmpty()){
      model.put("notificacionActiva","<i class=\"bi bi-bell\"></i>");
    }else {
      model.put("notificacionActiva","<i class=\"bi bi-bell-fill\"></i>");
    }


    List<Notificacion> notificaciones = repositorioNotificaciones.obtenerNotificacionesActivasXUsuario(usuarioAVerificar);

    List<Notificacion> restantes = notificaciones.stream()
        .filter(notificacion -> notificacion.getTipo() == TipoSuscripcion.RESTANTES.RESTANTES)
        .collect(Collectors.toList());
    List<Notificacion> faltantes = notificaciones.stream()
        .filter(notificacion -> notificacion.getTipo() == TipoSuscripcion.RESTANTES.FALTANTES)
        .collect(Collectors.toList());
    List<Notificacion> desperfecto = notificaciones.stream()
        .filter(notificacion -> notificacion.getTipo() == TipoSuscripcion.RESTANTES.DESPERFECTO)
        .collect(Collectors.toList());

    model.put("restantes",restantes);
    model.put("faltantes",faltantes);
    model.put("desperfecto",desperfecto);
    model.put("notificaciones",notificaciones);
    String tipoPersona = context.sessionAttribute("tipo_persona");
    model.put("tipoPersona",tipoPersona);
    context.render("/templates/notificaciones.mustache",model);
  }
}
