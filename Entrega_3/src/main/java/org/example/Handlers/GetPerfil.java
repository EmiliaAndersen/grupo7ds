package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Persona.PersonaHumana;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;

public class GetPerfil implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();
    Boolean succesLogin = context.sessionAttribute("succesLogin");
    String usuarioNombre = context.sessionAttribute("username");

    if ( succesLogin != null) {
      model.put("successMessage", "Session iniciada correctamente " + context.sessionAttribute("username"));
      context.sessionAttribute("succesLogin", null);
    }

    EntityManager em = BDUtils.getEntityManager();


    if(context.sessionAttribute("tipo_persona").equals("persona_humana")) {
      TypedQuery<PersonaHumana> query = em.createQuery(
              "SELECT p FROM PersonaHumana p JOIN p.usuario u WHERE u.usuario = :usu", PersonaHumana.class);
      query.setParameter("usu", usuarioNombre);

      PersonaHumana ph = query.getSingleResult();
      model.put("nombre", ph.nombre);
      model.put("apellido", ph.apellido);
      model.put("direccion", ph.getDireccion());
      model.put("fechaNacimiento", ph.getFechaDeNacimiento());
    }else{

    }

    context.render("/templates/perfil_"+context.sessionAttribute("tipo_persona")+".mustache", model);


  }
}
