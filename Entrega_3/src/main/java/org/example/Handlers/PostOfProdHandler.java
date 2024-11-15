package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.Factory.OfrecerProductosFactory;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.example.Dominio.Ofertas.Oferta;
import org.example.Dominio.Ofertas.TipoRubro;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class PostOfProdHandler implements Handler {

    public void handle(@NotNull Context ctx) {
        String rubro = ctx.formParam("rubro");
        String nombre = ctx.formParam("producto");

        String puntos = ctx.formParam("puntos");
        //int puntos = Integer.parseInt(ctx.formParam("puntos"));

        OfrecerProductos oferta = new OfrecerProductos(rubro, nombre, Integer.parseInt(puntos));

        System.out.println("Oferta de producto agregada");

        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        em.persist(oferta);
        em.getTransaction().commit(); // Confirmar la transacción
        ctx.redirect("/prodserv");
    }
}
