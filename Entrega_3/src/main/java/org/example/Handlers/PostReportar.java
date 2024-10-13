package org.example.Handlers;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PostReportar implements @NotNull Handler {
     public void handle(@NotNull Context context){

                // Obtener los datos del formulario
                String tipoFalla = context.formParam("heladera");
                String descripcion = context.formParam("motivo");
        
                // Validar que los campos no estén vacíos
                if (tipoFalla == null || tipoFalla.isEmpty() || descripcion == null || descripcion.isEmpty()) {
                    context.redirect("/reportar");  // Redirigir de nuevo a la página de reporte
                } else {
        
                    // Redirigir al perfil
                    context.redirect("/perfil");
                }
     }
}
