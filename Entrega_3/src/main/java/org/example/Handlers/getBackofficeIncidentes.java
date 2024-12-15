package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.Tecnico;
import org.example.repositorios.RepositorioColaboradores;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioIncidente;
import org.example.repositorios.RepositorioTecnicos;
import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class getBackofficeIncidentes implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();
    RepositorioTecnicos repositorioTecnicos = RepositorioTecnicos.getInstance();
    RepositorioIncidente repositorioIncidente = RepositorioIncidente.getInstance();
    RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();
    RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstance();

    String nombreUsuario = context.sessionAttribute("username");
    Tecnico tecnico = repositorioTecnicos.obtenerTecnicoXUsuario(nombreUsuario);

    List<Incidente> incidentes = repositorioIncidente.obtenerIncidentesXTecnicos(tecnico.getId());
    Collections.reverse(incidentes);

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
      if (alerta.getEstaActiva()){
        alertaInfo.put("estado","Activa");
      }else {
        alertaInfo.put("estado","Inactiva");
      }
      alertaInfo.put("visitas",alerta.getVisitas().size());
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
      String nombre = ((PersonaHumana) persona).getNombre();
      fallaInfo.put("id",fallaTecnica.getId());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      String fechaFormateada = fallaTecnica.getFechaYHora().format(formatter);
      fallaInfo.put("fecha", fechaFormateada);

      if (fallaTecnica.getEstaActiva()){
        fallaInfo.put("estado","Activo");
        fallaInfo.put("color","");
        fallaInfo.put("boton", "<button type=\"button\" class=\"btn btn-primary btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#modalVisitaFalla\" data-bs-id-incidente=\"{{id}}\">\n" +
            "                                                Registrar visita\n" +
            "                                            </button>");
      }else {
        fallaInfo.put("estado","Inactivo");
        fallaInfo.put("color","lightgreen");
      }
      fallaInfo.put("visitas",fallaTecnica.getVisitas().size());

      fallaInfo.put("nombreReportador",nombre);
      listaFallasData.add(fallaInfo);
    }
    model.put("fallasData",listaFallasData);


    model.put("incidentes",incidentes);
    context.render("/templates/backofficeIncidentesTecnicos.mustache",model);

  }
}
