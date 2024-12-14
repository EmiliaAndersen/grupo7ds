package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Tarjetas.SolicitudPermisos;
import org.example.Dominio.Tarjetas.TarjetaDistribuidor;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioTarjetasDist;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.List;

public class PostAutorizacion implements Handler {

    public void handle(@NotNull Context context) {

        EntityManager em = BDUtils.getEntityManager();

        String tarjeta_id_str = context.formParam("codigo_tarjeta");
        String heladera_id = context.formParam("heladera");

        if(tarjeta_id_str == null)
        {
            context.sessionAttribute("ErrorMotivo", true);
            context.redirect("/solicitar_autorizacion");
            return;
        }

        RepositorioTarjetasDist repoTarjetas = RepositorioTarjetasDist.getInstance();
        TarjetaDistribuidor tarjeta = repoTarjetas.obtenerTarjetaId(Long.parseLong(tarjeta_id_str));

        if(tarjeta == null){
            context.sessionAttribute("ErrorMotivo", true);
            context.redirect("/solicitar_autorizacion");
            return;
        }
        RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstance();
        Heladera heladera = repoHeladeras.obtenerHeladera(heladera_id);

        SolicitudPermisos solicitud = tarjeta.solicitarAcceso(heladera);

        BDUtils.comenzarTransaccion(em);
        em.persist(solicitud);
        BDUtils.commit(em);
        context.sessionAttribute("successSuscripcion", true);
        context.redirect("/solicitar_autorizacion");
    }
}