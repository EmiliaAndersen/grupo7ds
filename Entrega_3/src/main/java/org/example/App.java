package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Reportes.*;
import org.example.Dominio.PuntosEstrategicos.PuntoGeografico;
import org.example.Dominio.Rol.Admin;
import org.example.Dominio.Tarjetas.TarjetaDistribuidor;
import org.example.Handlers.*;
import org.example.Validador.Usuario;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioIncidente;
import org.example.repositorios.RepositorioTarjetasDist;
import org.example.repositorios.RepositorioUsuarios;
import org.example.repositorios.RepositorioVianda;
import org.example.Servicio.RecomendarHeladerasService;
import org.example.Servicio.UbicacionService;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public class App {
  public static void main(String[] args) {

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getRepositorioUsuarios();

    Javalin app = Javalin.create(javalinConfig -> {
              javalinConfig.staticFiles.add("/");
              javalinConfig.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
              });
            })
            .start(8085);

    // Aplica el middleware de autenticación antes de acceder a las rutas que requieren sesión


    if (repositorioUsuarios.verificarUsuarios("admin")) {
      Usuario usuario = new Usuario();
      usuario.setUsuario("admin");
      usuario.setContrasenia("admin");
      Admin admin = new Admin();
      PersonaHumana personaHumana = new PersonaHumana();
      personaHumana.setUsuario(usuario);
      personaHumana.asignarRol(personaHumana, admin);
      admin.setPersona(personaHumana);
      em.persist(usuario);
      em.persist(personaHumana);
      em.persist(admin);
      BDUtils.commit(em);
    }


    app.get("/cerrarSesion", new CerrarSesion());
    app.post("/cerrarSesion", new CerrarSesion());

    app.before("/backoffice", AuthMiddleware::verificarAutenticacion);
    app.get("/backoffice", new getBackoffice());
    app.post("/backoffice", new getBackoffice());


    app.before("/backoffice/tecnicos", AuthMiddleware::verificarAutenticacion);
    app.get("/backoffice/tecnicos", new getBackofficeTecnicos());
    app.post("/backoffice/tecnicos", new postBackofficeTecnicos());

    app.before("/backoffice/heladeras", AuthMiddleware::verificarAutenticacion);
    app.get("/backoffice/heladeras", new getBackofficeHeladeras());
    app.post("/backoffice/heladeras", new postBackofficeHeladeras());

    app.before("/notificaciones", AuthMiddleware::verificarAutenticacion);
    app.get("/notificaciones", new getNotificaciones());
    app.post("/notificaciones", new postNotificaciones());

    app.before("/backoffice/incidentes", AuthMiddleware::verificarAutenticacion);
    app.get("/backoffice/incidentes", new getBackofficeIncidentes());
    app.post("/backoffice/incidentes", new postBackofficeIncidentes());

    app.before("/front_page", AuthMiddleware::verificarAutenticacion);
    app.get("/front_page", new GetFrontPage());

    app.before("/upload_csv", AuthMiddleware::verificarAutenticacion);
    app.get("/upload_csv", new GetUploadCSV());
    app.post("/upload_csv", new PostUploadCSV());

    app.before("/", AuthMiddleware::verificarAutenticacion);
    app.before("", AuthMiddleware::verificarAutenticacion);
    app.get("", new GetPerfil());
    app.post("", new GetPerfil());
    app.get("/", new GetPerfil());
    app.post("/", new GetPerfil());

    app.get("/login", new LoginHandler());
    app.post("/login", new PostLoginHandler());

    app.get("/signin", new GetSignIn());
    app.post("/signin", new PostSignIn());


    app.before("/perfil_persona_humana", AuthMiddleware::verificarAutenticacion);
    app.get("/perfil_persona_humana", new GetPerfil());
    app.post("/perfil_persona_humana", new PostPerfil());

    app.before("/perfil_persona_juridica", AuthMiddleware::verificarAutenticacion);
    app.get("/perfil_persona_juridica", new GetPerfil());
    app.post("/perfil_persona_juridica", new PostPerfil());

    app.before("/colaboracion_persona_humana", AuthMiddleware::verificarAutenticacion);
    app.get("/colaboracion_persona_humana", new ColaboHumanaHandler());
    app.post("/colaboracion_persona_humana", new PostColaboHumanaHandler());

    app.before("/colaboracion_persona_juridica", AuthMiddleware::verificarAutenticacion);
    app.get("/colaboracion_persona_juridica", new ColaboJuridicaHandler());
    app.post("/colaboracion_persona_juridica", new PostColaboJuridicaHandler());

    app.before("/cargaColaboracion", AuthMiddleware::verificarAutenticacion);
    app.get("/cargaColaboracion", new CargaColaboHandler());
    app.post("/cargaColaboracion", new PostCargaColaboHandler());

    app.before("/ofrecer", AuthMiddleware::verificarAutenticacion);
    app.get("/ofrecer", new OfProdHandler());
    app.post("/ofrecer", new PostOfProdHandler());

    app.before("/personaVulnerable", AuthMiddleware::verificarAutenticacion);
    app.get("/personaVulnerable", new GetPersVulnHandler());
    app.post("/personaVulnerable", new PostPersVulnHandler());

    RepositorioVianda repoVianda = RepositorioVianda.getInstance();
    RepositorioIncidente repoIncidente = RepositorioIncidente.getInstance();

    FileService fileService = new CSVFileService();
    DataService dataService = new DataServiceRep(repoIncidente, repoVianda);

    List<GeneradorDeReportes> generadores = List.of(
            new ReporteFallasPorHeladera(dataService, fileService),
            new ReporteViandasPorColaborador(dataService, fileService),
            new ReporteViandasPorHeladera(dataService, fileService)
    );

    ReporteScheduler scheduler = new ReporteScheduler(generadores);
    scheduler.iniciarScheduler();

    app.before("/reportes", AuthMiddleware::verificarAutenticacion);
    app.get("/reportes", new GetReportes());
    app.post("/reportes", new PostReportes());

    app.before("/reportar", AuthMiddleware::verificarAutenticacion);
    app.get("/reportar", new GetReportar());
    app.post("/reportar", new PostReportar());

    app.before("/prodserv", AuthMiddleware::verificarAutenticacion);
    app.get("/prodserv", new GetProdServ());
    app.post("/prodserv", new PostProdServ());


    app.post("/auth/google", new AuthController());


    app.before("/suscripcion", AuthMiddleware::verificarAutenticacion);
    app.get("/suscripcion", new GetSuscripcion());
    app.post("/suscripcion", new PostSuscripcion());

    app.before("/heladeras", AuthMiddleware::verificarAutenticacion);
    app.get("/heladeras", new GetHeladera());
    app.post("/heladeras", new PostHeladera());

    app.get("/recomendarHeladeras", RecomendarHeladerasService::apiRecomendacion);

    app.before("/canjear-producto/{id}", AuthMiddleware::verificarAutenticacion);
    app.post("/canjear-producto/{id}", new CanjearProductoHandler());

    app.before("/colaboraciones_realizadas", AuthMiddleware::verificarAutenticacion);
    app.get("/colaboraciones_realizadas", new GetColaboracionesRealizadas());
    app.post("/colaboraciones_realizadas", new PostColabos());

    RepositorioTarjetasDist repositorioTarjetasDist = RepositorioTarjetasDist.getInstance();
    for(int i = 1; i <= 5; i++){
        TarjetaDistribuidor tarjetaDistribuidor = new TarjetaDistribuidor();
        repositorioTarjetasDist.addTarjeta(tarjetaDistribuidor);
    }

    app.before("/solicitar_autorizacion", AuthMiddleware::verificarAutenticacion);
    app.get("/solicitar_autorizacion", new GetAutorizacion());
    app.post("/solicitar_autorizacion", new PostAutorizacion());

    app.before("/micrometer/metrics", AuthMiddleware::verificarAutenticacion);
    app.get("/micrometer/metrics", new GetMicrometerMetrics());

    app.before("/notificaciones/redistribucion", AuthMiddleware::verificarAutenticacion);
    app.get("/notificaciones/redistribucion", new GetDistribucionHandler());
    app.post("/notificaciones/redistribucion", new PostDistribucion());


    app.get("/recomendarUbicacion", ctx -> {
      UbicacionService ubicacionService = new UbicacionService();

      try {
        double latitude = -34.604;
        double longitude = -58.381;
        int radioKm = 10000;

        List<PuntoGeografico> puntos = ubicacionService.getNearbyPoints(latitude, longitude, radioKm);
        ctx.json(puntos); // Return as JSON to the frontend
      } catch (Exception e) {
        ctx.status(500).result("Error contacting nearby points API: " + e.getMessage());
      }
    });


  }


  class AuthMiddleware {
    public static void verificarAutenticacion(Context context) {
      List<String> rutasPublicas = List.of("/login", "/signin");
      String username = context.sessionAttribute("username");
      String rol = "noAdmin";
      if (context.sessionAttribute("rol") != null) {
        rol = context.sessionAttribute("rol");
      }
      if (!rutasPublicas.contains(context.path()) && (username == null || username.isEmpty())) {
        context.redirect("/login");
      }
      //REVISAR
    /*if(!rol.equals("admin")  && (context.path().equals("/backoffice") || context.path().equals("/backoffice/heladeras") || context.path().equals("/backoffice/tecnicos"))){
      context.redirect("/perfil_"+context.sessionAttribute("tipo_persona"));
    }


    //REVISAR
    if(rol.equals("admin")  && !context.path().equals("/backoffice") && !context.path().equals("/backoffice/tecnicos")  && !context.path().equals("/backoffice/heladeras") ){
      context.redirect("/backoffice/"+context.path());
    }*/


    }
  }
}
