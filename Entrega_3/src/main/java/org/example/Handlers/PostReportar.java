package org.example.Handlers;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Incidentes.IncidenteFactory;
import org.example.Dominio.Rol.Colaborador;
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
        
                // Validar que los campos no estén vacíos
                if (heladera_id == null || heladera_id.isEmpty() || descripcion == null || descripcion.isEmpty()) {
                    context.redirect("/reportar");  // Redirigir de nuevo a la página de reporte
                } else {
                    Heladera heladera = repoHeladeras.obtenerHeladera(heladera_id);
                    if (heladera == null) {
                        model.put("errorMessage", "La heladera no existe");
                        context.render("/templates/colaboracionHumana.mustache", model);
                        return;
                    }
                    IncidenteFactory incidenteFactory = new IncidenteFactory();
                    Colaborador col = repoColaboradores.obtenerColaboradorxID(2L);
                    Incidente falla = incidenteFactory.crearFalla(col, heladera);
                    // Redirigir al perfil
                    repoIncidentes.addIncidente(falla);
                    context.redirect("/reportar");//agregar tipo persona al path
                }
     }
}
