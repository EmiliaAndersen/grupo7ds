package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.example.BDUtils;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.PersonaVulnerable;
import org.example.Dominio.Rol.TipoSituacion;
import org.jetbrains.annotations.NotNull;

public class PostPersVulnHandler implements @NotNull Handler {
    
     public void handle(@NotNull Context context) {
         EntityManager em = BDUtils.getEntityManager();
         BDUtils.comenzarTransaccion(em);

         Map<String, Object> model = new HashMap<>();

         String nombre = context.formParam("nombre");
         String fechaNacimiento = context.formParam("fecha_nacimiento");
         String frecuencia = context.formParam("frecuencia");
         String menoresCargo = context.formParam("menores_cargo");

         if (isNullOrEmpty(nombre) || isNullOrEmpty(fechaNacimiento) || isNullOrEmpty(frecuencia) || isNullOrEmpty(menoresCargo)) {
             model.put("errorMessage", "Error al registrar la persona, complete todos los datos correctamente");
             context.sessionAttribute("errorPost", true);
         } else {
             PersonaVulnerable pv = new PersonaVulnerable();
             pv.setNombre(nombre);
             pv.fechaDeNac = localDateConverter(fechaNacimiento);
             pv.setTipo(convertidor(frecuencia));
             Documento documento = new Documento(0123, "asd", "asdas", "asd");
             pv.setDocumento(documento);
             pv.setMenoresACargo(stringToInt(menoresCargo));

             em.persist(documento);
             em.persist(pv);
             BDUtils.commit(em);

             model.put("successMessage", "Persona registrada correctamente");
             context.sessionAttribute("successPost", true);
         }
         context.redirect("/personaVulnerable");
     }
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
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
        }else{
            return TipoSituacion.DOMICILIO;
        }
    }
}
