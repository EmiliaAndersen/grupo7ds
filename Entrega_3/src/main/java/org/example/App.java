package org.example;

import io.javalin.Javalin;
import org.example.Handlers.*;

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
        .get("/", ctx -> ctx.result("Server iniciado :)"))
        .start(8085);

    //Utilizo get y el path para crear esa ruta, despues creo un handler para manejarla desde ahi
    //Utilizo posy y el path de esa ruta, despues creo un handler para manejarlo desde ahi
    app.get("/login",  new LoginHandler());
    app.post("/login", new PostLoginHandler());

    app.get("/signin", new GetSignIn());
    app.post("/signin", new PostSignIn());


    app.before("/perfil", new GetPerfil());

    app.get("/colaboracionHumana", new ColaboHumanaHandler());
    app.post("/colaboracionHumana", new PostColaboHumanaHandler());

    app.get("/colaboracionJuridica", new ColaboJuridicaHandler());
    app.post("/colaboracionJuridica", new PostColaboJuridicaHandler());

    app.get("/cargaColaboracion", new CargaColaboHandler());
    app.post("/cargaColaboracion", new PostCargaColaboHandler());

    app.get("/ofrecer", new OfProdHandler());
    app.post("/ofrecer", new PostOfProdHandler());

    app.get("/reportes", new GetReportes());
    app.post("/reportes", new PostReportes());

    app.get("/reportar", new GetReportar());
    app.post("/reportar", new PostReportar());

    app.get("/prodserv", new GetProdServ());
    app.post("/prodserv", new PostProdServ());

    app.get("/suscripcion", new GetSuscripcion());
    app.post("/suscripcion", new PostSuscripcion());

    app.get("/heladeras", new GetHeladera());
    app.post("/heladeras", new PostHeladera());
  }
}
