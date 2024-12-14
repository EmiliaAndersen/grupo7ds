package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

public class GetAutorizacion implements Handler {

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
                model.put("ErrorMotivo","Tarjeta invalida");
                context.sessionAttribute("ErrorMotivo",false);
            }
        }

        boolean successSuscription = false;
        if (context.sessionAttribute("successSuscripcion") != null) {
            successSuscription = context.sessionAttribute("successSuscripcion");
            if (successSuscription) {
                model.put("successSuscripcion", "Permiso otorgado correctamente");
                context.sessionAttribute("successSuscripcion", false);
            }
        }

        boolean error = false;


        context.render("/templates/permiso.mustache", model);
    }
}
