package org.example.Handlers;

import java.util.HashMap;
import java.util.List;

import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class GetProdServ implements @NotNull Handler {
  public void handle(@NotNull Context context){
        var model = new HashMap<String, Object>();
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        TypedQuery<OfrecerProductos> query = em.createQuery("select o from OfrecerProductos o",OfrecerProductos.class);
        List<OfrecerProductos> productos = query.getResultList();

        model.put("productos",productos);
        String tipoPersona = context.sessionAttribute("tipo_persona");
        if (tipoPersona != null) {
              model.put("tipoPersona", tipoPersona);
        }

        context.render("/templates/prodserv.mustache", model);
  }
}
