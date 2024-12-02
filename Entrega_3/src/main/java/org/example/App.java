package org.example;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Handlers.*;
import java.util.List;

import javax.persistence.EntityManager;

public class App {
  public static void main(String[] args) {

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    Javalin app = Javalin.create(javalinConfig -> {
              javalinConfig.staticFiles.add("/");
              javalinConfig.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
              });
            })
            .start(8085);

    // Aplica el middleware de autenticación antes de acceder a las rutas que requieren sesión


    app.before("/",AuthMiddleware::verificarAutenticacion);
    app.before("",AuthMiddleware::verificarAutenticacion);
    app.get("",  new GetPerfil());
    app.post("", new GetPerfil());
    app.get("/",  new GetPerfil());
    app.post("/", new GetPerfil());

    app.get("/login",  new LoginHandler());
    app.post("/login", new PostLoginHandler());

    app.get("/signin", new GetSignIn());
    app.post("/signin", new PostSignIn());


    app.before("/perfil_persona_humana",AuthMiddleware::verificarAutenticacion);
    app.get("/perfil_persona_humana", new GetPerfil());
    app.post("/perfil_persona_humana", new PostPerfil());

    app.before("/perfil_persona_juridica",AuthMiddleware::verificarAutenticacion);
    app.get("/perfil_persona_juridica", new GetPerfil());
    app.post("/perfil_persona_juridica", new PostPerfil());

    app.before("/colaboracion_persona_humana",AuthMiddleware::verificarAutenticacion);
    app.get("/colaboracion_persona_humana", new ColaboHumanaHandler());
    app.post("/colaboracion_persona_humana", new PostColaboHumanaHandler());

    app.before("/colaboracion_persona_juridica",AuthMiddleware::verificarAutenticacion);
    app.get("/colaboracion_persona_juridica", new ColaboJuridicaHandler());
    app.post("/colaboracion_persona_juridica", new PostColaboJuridicaHandler());

    app.before("/cargaColaboracion",AuthMiddleware::verificarAutenticacion);
    app.get("/cargaColaboracion", new CargaColaboHandler());
    app.post("/cargaColaboracion", new PostCargaColaboHandler());

    app.before("/ofrecer",AuthMiddleware::verificarAutenticacion);
    app.get("/ofrecer", new OfProdHandler());
    app.post("/ofrecer", new PostOfProdHandler());

    app.before("/personaVulnerable",AuthMiddleware::verificarAutenticacion);
    app.get("/personaVulnerable", new GetPersVulnHandler());
    app.post("/personaVulnerable", new PostPersVulnHandler());

    app.before("/reportes",AuthMiddleware::verificarAutenticacion);
    app.get("/reportes", new GetReportes());
    app.post("/reportes", new PostReportes());

    app.before("/reportar",AuthMiddleware::verificarAutenticacion);
    app.get("/reportar", new GetReportar());
    app.post("/reportar", new PostReportar());

    app.before("/prodserv",AuthMiddleware::verificarAutenticacion);
    app.get("/prodserv", new GetProdServ());
    app.post("/prodserv", new PostProdServ());

    app.before("/suscripcion",AuthMiddleware::verificarAutenticacion);
    app.get("/suscripcion", new GetSuscripcion());
    app.post("/suscripcion", new PostSuscripcion());

    app.before("/heladeras",AuthMiddleware::verificarAutenticacion);
    app.get("/heladeras", new GetHeladera());
    app.post("/heladeras", new PostHeladera());

    app.before("/canjear-producto/{id}",AuthMiddleware::verificarAutenticacion);
    app.post("/canjear-producto/{id}", new CanjearProductoHandler());

    app.before("/colaboraciones_realizadas",AuthMiddleware::verificarAutenticacion);
    app.get("/colaboraciones_realizadas", new GetColaboracionesRealizadas());
  }
}

class AuthMiddleware {
  public static void verificarAutenticacion(Context context) {
    List<String> rutasPublicas = List.of("/login", "/signin");
    String username = context.sessionAttribute("username");
    if (!rutasPublicas.contains(context.path()) && (username == null || username.isEmpty())) {
      context.redirect("/login");
    }
    String tipoPersona = context.sessionAttribute("tipo_persona");
    if (tipoPersona != null) {
      context.attribute("tipoPersona", tipoPersona);
    }
  }
}
