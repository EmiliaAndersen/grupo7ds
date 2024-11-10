package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Persona.PersonaHumana;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;

public class GetPerfil implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();
    Boolean succesLogin = context.sessionAttribute("succesLogin");

    if ( succesLogin != null && succesLogin) {
      model.put("successMessage", "Session iniciada correctamente " + context.sessionAttribute("username"));
      //context.sessionAttribute("succesLogin", null);  TODO: porque esta linea existe?
      EntityManager em = BDUtils.getEntityManager();
      TypedQuery<PersonaHumana> query = em.createQuery(
          "SELECT p FROM PersonaHumana p JOIN p.usuario u WHERE u.usuario = :usu", PersonaHumana.class);
      query.setParameter("usu", "dan");

      PersonaHumana ph = query.getSingleResult();

      model.put("nombre",ph.nombre);
      model.put("apellido", ph.apellido);
      model.put("direccion",ph.getDireccion());
      model.put("fechaNacimiento", ph.getFechaDeNacimiento());

    }
    context.render("/templates/perfil.mustache", model);




  }
}
