package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Suscripciones.Notificacion;
import org.example.repositorios.RepositorioNotificaciones;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.Map;

public class postNotificaciones implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    EntityManager em = BDUtils.getEntityManager();
    RepositorioNotificaciones repositorioNotificaciones = RepositorioNotificaciones.getInstance();

    Long idNotificacion = ((Number) context.bodyAsClass(Map.class).get("id")).longValue();


    Notificacion notificacion = repositorioNotificaciones.buscarNotificacionPorId(idNotificacion);
    if (notificacion != null) {
      BDUtils.comenzarTransaccion(em);
      notificacion.setEstaResuelta(true);
      em.merge(notificacion);
      BDUtils.commit(em);
      context.redirect("/notificaciones");

    } else {

    }

  }
}
