package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Colaboraciones.Factory.OfrecerProductosFactory;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.jetbrains.annotations.NotNull;

public class PostOfProdHandler implements Handler {

    public void handle(@NotNull Context ctx) {
        String rubro = ctx.formParam("rubro");
        String nombre = ctx.formParam("producto");
        int puntos = Integer.parseInt(ctx.formParam("puntos"));

        new OfrecerProductos(rubro, nombre, puntos);

        System.out.println("Oferta de producto agregada");
        ctx.redirect("/perfil");
    }
}
