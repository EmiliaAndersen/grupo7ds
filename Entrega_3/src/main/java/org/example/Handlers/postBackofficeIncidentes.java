
package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Incidentes.Visita;
import org.example.Dominio.Rol.Tecnico;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioIncidente;
import org.example.repositorios.RepositorioTecnicos;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class postBackofficeIncidentes implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();

    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    RepositorioTecnicos repositorioTecnicos = RepositorioTecnicos.getInstance();
    RepositorioIncidente repositorioIncidente = RepositorioIncidente.getInstance();

    String usuarioNombre = context.sessionAttribute("username");

    try {
      Tecnico tecnico = repositorioTecnicos.obtenerTecnicoXUsuario(usuarioNombre);


      Incidente incidente = repositorioIncidente.obtenerIncidenteXID(Long.parseLong(context.formParam("id_incidente")));

      Boolean pudoResolver;
      if (context.formParam("resultado").equals("1")) {
        pudoResolver = true;
        incidente.setEstaActiva(false);
        em.merge(incidente);
        RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();
        Heladera heladera = repositorioHeladeras.obtenerHeladeraXIncidente(incidente);
        List<Incidente> incidentesHeladera = repositorioIncidente.obtenerIncidentesActivosXHeladera(heladera);
        if (incidentesHeladera.size() <= 1){
          heladera.setEstado(EstadoHeladera.ACTIVA);
          em.merge(heladera);
        }
      } else {
        pudoResolver = false;
      }


      Visita visita = new Visita(tecnico,LocalDateTime.now() , pudoResolver, context.formParam("descripcion"), incidente);
      incidente.getVisitas().add(visita);
      em.persist(visita);
      BDUtils.commit(em);
    }catch (Exception e){
      context.sessionAttribute("errorMessage","Error al crear la visita");
    }


    context.redirect("/backoffice/incidentes");
  }

  public LocalDateTime localDateConverter(String fecha){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDateTime.parse(fecha, formatter);
  }
}
