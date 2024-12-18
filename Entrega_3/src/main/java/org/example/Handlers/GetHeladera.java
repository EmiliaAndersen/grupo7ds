package org.example.Handlers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.DonacionDeDinero;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Persona.PersonaJuridica;
import org.example.Dominio.Rol.Colaborador;
import org.example.repositorios.RepositorioColaboradores;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioNotificaciones;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class GetHeladera implements @NotNull Handler {
  public void handle(@NotNull Context context){
  var model = new HashMap<String, Object>();
    RepositorioNotificaciones repositorioNotificaciones = RepositorioNotificaciones.getInstance();
    String usuarioAVerificar = context.sessionAttribute("username");
    if (repositorioNotificaciones.obtenerNotificacionesActivasXUsuario(usuarioAVerificar).isEmpty()){
      model.put("notificacionActiva","<i class=\"bi bi-bell\"></i>");
    }else {
      model.put("notificacionActiva","<i class=\"bi bi-bell-fill\"></i>");
    }
  EntityManager em = BDUtils.getEntityManager();
  BDUtils.comenzarTransaccion(em);
    RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstance();
    RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();

    TypedQuery<Heladera> query = em.createQuery(
        "SELECT h FROM Heladera h " +
            "JOIN h.suscriptores s " +
            "JOIN s.colaborador c " +
            "JOIN c.persona p " +
            "JOIN p.usuario u " +
            "WHERE u.usuario = :usu", Heladera.class
    );

    String usuarioNombre = context.sessionAttribute("username");
    query.setParameter("usu", usuarioNombre);

    List<Heladera> heladerass = query.getResultList();


    List<Heladera> heladeras = heladerass.stream()
    .distinct() 
    .collect(Collectors.toList());


    model.put("heladeras",heladeras);

    String localizaciones ="";
    for (Heladera heladera : heladeras) {
        if(localizaciones == ""){
          localizaciones = heladera.getUbicacion().getNombre() + ","+heladera.getUbicacion().getLatitud() + ","+ heladera.getUbicacion().getLongitud();
        }else{
          localizaciones = localizaciones + ";"+ heladera.getUbicacion().getNombre() + ","+ heladera.getUbicacion().getLatitud() + ","+ heladera.getUbicacion().getLongitud();
        }
    }
    model.put("localizaciones",localizaciones);

    TypedQuery<Incidente> queryIncidente = em.createQuery(
        "SELECT i FROM Incidente i " +
            "JOIN i.heladera h " +
            "JOIN h.suscriptores s " +
            "JOIN s.colaborador c " +
            "JOIN c.persona p " +
            "JOIN p.usuario u " +
            "WHERE u.usuario = :usu AND i.estaActiva = true", Incidente.class
    );


    queryIncidente.setParameter("usu", usuarioNombre);

    List<Incidente> incidentess = queryIncidente.getResultList();

    List<Incidente> incidentes  = incidentess.stream()
    .distinct() 
    .collect(Collectors.toList());


    List<Alerta> alertas = incidentes.stream()
        .filter(i -> i instanceof Alerta)
        .map(i -> (Alerta) i)
        .toList();


    List<Map<String, Object>> listaAlertasData = new ArrayList<>();


    for (Alerta alerta : alertas) {
      Map<String, Object> alertaInfo = new HashMap<>();
      Long id = alerta.heladera.getId();
      Heladera heladera = repositorioHeladeras.obtenerHeladera(id.toString());
      alertaInfo.put("nombreHeladera", heladera.getUbicacion().getNombre());
      alertaInfo.put("alertaTipo",alerta.getTipo());

      listaAlertasData.add(alertaInfo);
    }
    model.put("fallasData",listaAlertasData);

    List<FallaTecnica> fallaTecnicas = incidentes.stream()
        .filter(i -> i instanceof FallaTecnica)
        .map(i -> (FallaTecnica) i)
        .toList();
    List<Map<String, Object>> listaFallasData = new ArrayList<>();

    for (FallaTecnica fallaTecnica : fallaTecnicas) {
      Map<String, Object> fallaInfo = new HashMap<>();
      Long id = fallaTecnica.heladera.getId();
      Heladera heladera = repositorioHeladeras.obtenerHeladera(id.toString());
      fallaInfo.put("nombreHeladera", heladera.getUbicacion().getNombre());
      fallaInfo.put("descripcionTecnica",fallaTecnica.getDescription());

      Colaborador colaborador = repositorioColaboradores.obtenerColaboradorxID(fallaTecnica.getReportero().getId());
      Persona persona = colaborador.getPersona();
      String nombre = null;
      if(persona instanceof PersonaHumana){
        nombre = ((PersonaHumana) persona).getNombre();
      }else if (persona instanceof PersonaJuridica){
        nombre = ((PersonaJuridica) persona).getRazonSocial();
      }
      fallaInfo.put("id",fallaTecnica.getId());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      String fechaFormateada = fallaTecnica.getFechaYHora().format(formatter);
      fallaInfo.put("fecha", fechaFormateada);

      fallaInfo.put("nombreReportador",nombre);
      listaFallasData.add(fallaInfo);
    }
    model.put("fallasData",listaFallasData);



    model.put("tipoPersona", context.sessionAttribute("tipo_persona"));
    context.render("/templates/heladeras.mustache", model);

  }
}
