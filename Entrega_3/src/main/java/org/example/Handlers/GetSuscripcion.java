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
        BDUtils.comenzarTransaccion(em);

        try {
            List<Heladera> heladeras = em.createQuery("SELECT h FROM Heladera h", Heladera.class)
                                         .getResultList();
            if (heladeras.isEmpty()) {
                context.status(404).result("No se encontraron heladeras.");
                return;
            }

            model.put("heladeras", heladeras);
            context.render("/templates/suscripcion.mustache", model);

            BDUtils.commit(em);
        } catch (Exception e) {
            BDUtils.rollback(em);
            e.printStackTrace();  // Verifica el error en la consola
            context.status(500).result("Error al obtener heladeras: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
