package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Heladeras.Heladera;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioNotificaciones;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class ColaboJuridicaHandler implements Handler {

    public void handle(@NotNull Context ctx) {
        var model = new HashMap<String, Object>();
        RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();
        RepositorioNotificaciones repositorioNotificaciones = RepositorioNotificaciones.getInstance();
        String usuarioAVerificar = ctx.sessionAttribute("username");
        if (repositorioNotificaciones.obtenerNotificacionesActivasXUsuario(usuarioAVerificar).isEmpty()){
            model.put("notificacionActiva","<i class=\"bi bi-bell\"></i>");
        }else {
            model.put("notificacionActiva","<i class=\"bi bi-bell-fill\"></i>");
        }

        List<Heladera> heladeras = repositorioHeladeras.obtenerTodasHeladeras();

        model.put("heladeras",heladeras);
        model.put("tipoPersona", ctx.sessionAttribute("tipo_persona"));
        ctx.render("/templates/colaboracionJuridica.mustache", model);
    }
}
