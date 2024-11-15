package org.example.Handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Persona.PersonaHumana;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class GetHeladera implements @NotNull Handler {
  public void handle(@NotNull Context context){
        var model = new HashMap<String, Object>();
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);

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

    List<Heladera> heladeras = query.getResultList();

    model.put("heladeras",heladeras);

    String localizaciones ="";
    for (Heladera heladera : heladeras) {
        if(localizaciones == ""){
          localizaciones = heladera.getUbicacion().getLatitud() + ","+ heladera.getUbicacion().getLongitud();
        }else{
          localizaciones = localizaciones + ";"+ heladera.getUbicacion().getLatitud() + ","+ heladera.getUbicacion().getLongitud();
        }
    }
    model.put("localizaciones",localizaciones);

    TypedQuery<Incidente> queryAlerta = em.createQuery(
        "SELECT i FROM Incidente i " +
            "JOIN i.heladera h " +
            "JOIN h.suscriptores s " +
            "JOIN s.colaborador c " +
            "JOIN c.persona p " +
            "JOIN p.usuario u " +
            "WHERE u.usuario = :usu", Incidente.class
    );


    queryAlerta.setParameter("usu", usuarioNombre);

    List<Incidente> incidentes = queryAlerta.getResultList();

    List<Map<String, Object>> incidentesData = new ArrayList<>();

    for (Incidente incidente : incidentes) {
      Map<String, Object> incidenteData = new HashMap<>();
      incidenteData.put("id", incidente.getId()); // Asegúrate de tener un método getId en tu clase Incidente
      incidenteData.put("tipo", incidente instanceof Alerta ? "Alerta" : "FallaTecnica");

      // Atributos comunes
      incidenteData.put("descripcion", incidente.getFechaYHora());

      // Atributos específicos con banderas
      if (incidente instanceof Alerta) {
        Alerta alerta = (Alerta) incidente;
        incidenteData.put("mostrarAlerta", true);
        incidenteData.put("nivelAlerta", alerta.getTipo()); // Cambia según tu enum
      } else if (incidente instanceof FallaTecnica) {
        FallaTecnica falla = (FallaTecnica) incidente;
        incidenteData.put("mostrarFallaTecnica", true);
        incidenteData.put("descripcionTecnica", falla.getDescription());
      }

      incidentesData.add(incidenteData);
    }


    model.put("incidentesData", incidentesData);


    model.put("incidentes",incidentes);

    context.render("/templates/heladeras.mustache", model);

  }
}
