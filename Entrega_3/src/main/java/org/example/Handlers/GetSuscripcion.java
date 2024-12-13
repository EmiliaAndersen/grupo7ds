package org.example.Handlers;

import java.util.HashMap;
import java.util.List;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class GetSuscripcion implements @NotNull Handler {
    public void handle(@NotNull Context context) {
        var model = new HashMap<String, Object>();
        model.put("tipoPersona", context.sessionAttribute("tipo_persona"));

        EntityManager em = BDUtils.getEntityManager();


            List<Heladera> heladeras = em.createQuery("SELECT h FROM Heladera h", Heladera.class)
                                         .getResultList();
     
            model.put("heladeras", heladeras);
            boolean errorMotivo = false;

            if(context.sessionAttribute("ErrorMotivo") != null){
                errorMotivo = context.sessionAttribute("ErrorMotivo");
                if(errorMotivo){
                  model.put("ErrorMotivo","Motivo no valido: Ponga 1,2 o 3");
                  context.sessionAttribute("ErrorMotivo",false);
                }
            }

            boolean successSuscripcion = false;
            if(context.sessionAttribute("successSuscripcion") != null){
                successSuscripcion = context.sessionAttribute("successSuscripcion");
                if(successSuscripcion){
                  model.put("successSuscripcion","Se suscribio correctamente a la heladera");
                  context.sessionAttribute("successSuscripcion",false);
                }
            }
            context.render("/templates/suscripcion.mustache", model);
           
    }

}