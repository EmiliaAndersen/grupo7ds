package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.Factory.OfrecerProductosFactory;
import org.example.Dominio.Colaboraciones.OfrecerProductos;
import org.example.Dominio.Ofertas.Oferta;
import org.example.Dominio.Ofertas.TipoRubro;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

public class PostOfProdHandler implements Handler {

    public void handle(@NotNull Context ctx) {


        var model = new HashMap<String, Object>();

        String rubro = ctx.formParam("rubro");
        String nombre = ctx.formParam("producto");

        String puntos = ctx.formParam("puntos");
        //int puntos = Integer.parseInt(ctx.formParam("puntos"));
        

        if( rubro.isEmpty() || nombre.isEmpty() || puntos.isEmpty()){
            System.out.println("Complete el/los campo correctamente");

            model.put("errorOfrecerProd", "CompleARRRRRR");
            ctx.sessionAttribute("errorOfrecerProd", true);
            ctx.render("/templates/ofrecer.mustache", model);

            return;
        }

        if (!esEntero(puntos)) {

            System.out.println("Complete el campo correctamente");
            model.put("errorOfrecerProd", "Complete el campo correctamente");
            ctx.sessionAttribute("errorOfrecerProd", true);
            ctx.render("/templates/ofrecer.mustache", model);
   
            return;
        }
            
    
        else{
        OfrecerProductos oferta = new OfrecerProductos(rubro, nombre, Integer.parseInt(puntos));


        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

        em.persist(oferta);
        em.getTransaction().commit(); // Confirmar la transacción
        
        System.out.println("Se ofrecio el producto correctamente");

 
        model.put("successOfrecerProd", "Se ofrecio el producto correctamente");
        ctx.sessionAttribute("successOfrecerProd", true);
        ctx.render("/templates/ofrecer.mustache", model);
        
    }}


    public boolean esEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;  // Si no lanza una excepción, es un número entero válido
        } catch (NumberFormatException e) {
            return false; // Si lanza la excepción, no es un entero válido
        }
    }
}
