package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;

import org.example.BDUtils;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.PersonaVulnerable;
import org.example.Dominio.Rol.TipoSituacion;
import org.jetbrains.annotations.NotNull;

public class PostPersVulnHandler implements @NotNull Handler {
    
     public void handle(@NotNull Context context){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);


      PersonaVulnerable pv = new PersonaVulnerable();
      pv.setNombre(context.formParam("nombre"));
      pv.fechaDeNac = localDateConverter(context.formParam("fecha_nacimiento"));
      pv.setTipo(convertidor(context.formParam("frecuencia")));
      Documento documento = new Documento(0123,"asd","asdas","asd");  
      pv.setDocumento(documento);
      pv.setMenoresACargo(stringToInt(context.formParam("menores_cargo")));

      em.persist(documento);
      em.persist(pv);
      BDUtils.commit(em);
                // Obtener los datos del formulario
            //EJEMPLO:
                /*String tipoFalla = context.formParam("heladera");
                String descripcion = context.formParam("motivo");
        
                // Validar que los campos no estén vacíos
                if (tipoFalla == null || tipoFalla.isEmpty() || descripcion == null || descripcion.isEmpty()) {
                    context.redirect("/reportar");  // Redirigir de nuevo a la página de reporte
                } else {
        
                    // Redirigir al perfil
                    context.redirect("/perfil");
                }*/

                context.redirect("/perfil"); //VER
     }

  public static int stringToInt(String str) {
        return Integer.parseInt(str);
    }
        public LocalDate localDateConverter(String fecha){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(fecha, formatter);
        }

        public TipoSituacion convertidor(String frecuencia){
            if("sin-vivienda".equals(frecuencia)){
                return TipoSituacion.SINVIVIENDA;
            }
            else{
                return TipoSituacion.DOMICILIO;
            }
    }
}
