package org.example.Handlers;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Incidentes.IncidenteFactory;
import org.example.Dominio.Notificador.Notificador;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.repositorios.RepositorioColaboradores;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioIncidente;
import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;

public class PostReportar implements @NotNull Handler {
     public void handle(@NotNull Context context){

       // Obtener los datos del formulario
       String heladera_id = context.formParam("heladera");
       String descripcion = context.formParam("motivo");
       RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstance();
       RepositorioIncidente repoIncidentes = RepositorioIncidente.getInstance();
       RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstance();
       Map<String, Object> model = new HashMap<>();
       Notificador notificador = Notificador.getInstance();

                 String userName = context.sessionAttribute("username");
                 Colaborador colaborador = repoColaboradores.obtenerColaborador(userName);
                 if (colaborador == null) {
                     model.put("errorReportar", "El colaborador no existe");
                     context.render("/templates/colaboracionHumana.mustache", model);
                     return;
                 }
        
                // Validar que los campos no estén vacíos
                if (heladera_id == null || heladera_id.isEmpty() || descripcion == null || descripcion.isEmpty()) {
                


                    model.put("errorReportar", "La heladera no existe");
                    context.render("/templates/colaboracionHumana.mustache", model);
                    context.sessionAttribute("errorReportar", true);
                    context.redirect("/reportar");
                     return;


                } else {
                    Heladera heladera = repoHeladeras.obtenerHeladera(heladera_id);
                    if (heladera == null) {
                        model.put("errorReportar", "La heladera no existe");
                        context.render("/templates/colaboracionHumana.mustache", model);
                        context.sessionAttribute("errorReportar", true);
                        context.redirect("/reportar");
                        return;
                    }
                    IncidenteFactory incidenteFactory = new IncidenteFactory();
                    Incidente falla = incidenteFactory.crearFalla(colaborador, heladera, descripcion);
            
                    repoIncidentes.addIncidente(falla);
                    notificador.movimientoHeladera(heladera, TipoSuscripcion.DESPERFECTO);
                    context.sessionAttribute("successReportar", true);

                    context.redirect("/reportar");//agregar tipo persona al path
                }
     }
}
