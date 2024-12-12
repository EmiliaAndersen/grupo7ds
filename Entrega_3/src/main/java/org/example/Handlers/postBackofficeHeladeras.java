package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class postBackofficeHeladeras implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    String boton = context.formParam("value-submit");

    if ("2".equals(boton)) {
        context.sessionAttribute("username", null);
        context.redirect("/login");
    } else {

      String nombre = context.formParam("nombre");
      double longitud = Double.parseDouble(context.formParam("longitud"));
      double latitud = Double.parseDouble(context.formParam("latitud"));
      String direccion = context.formParam("direccion");
      double temperaturaMaxima = Double.parseDouble(context.formParam("temperaturaMaxima"));
      double temperaturaMinima = Double.parseDouble(context.formParam("temperaturaMinima"));
      Integer capacidadViandas = Integer.parseInt(context.formParam("capacidadViandas"));

      PuntoEstrategico punto = new PuntoEstrategico(nombre, longitud, latitud, direccion);

      Heladera heladera = new Heladera((int) temperaturaMaxima, (int) temperaturaMinima, punto, capacidadViandas);
      Map<String, Object> model = new HashMap<>();
      try {
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(punto);
        em.persist(heladera);
        BDUtils.commit(em);
        context.sessionAttribute("successMessage", true);
      } catch (Exception e) {
          e.printStackTrace();
          context.sessionAttribute("errorMessage", true);
      }

      context.redirect("/backoffice/heladeras");
    }
  }
}
