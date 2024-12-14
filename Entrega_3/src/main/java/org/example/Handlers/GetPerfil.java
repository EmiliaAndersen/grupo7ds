package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Persona.PersonaJuridica;
import org.example.repositorios.RepositorioNotificaciones;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;

public class GetPerfil implements Handler {

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();
    RepositorioNotificaciones repositorioNotificaciones = RepositorioNotificaciones.getInstance();
    String usuarioAVerificar = context.sessionAttribute("username");
    if (repositorioNotificaciones.obtenerNotificacionesActivasXUsuario(usuarioAVerificar).isEmpty()){
      model.put("notificacionActiva","<i class=\"bi bi-bell\"></i>");
    }else {
      model.put("notificacionActiva","<i class=\"bi bi-bell-fill\"></i>");
    }
    Boolean succesLogin = context.sessionAttribute("succesLogin");
    Boolean successModify = context.sessionAttribute("successModify");
    String usuarioNombre = context.sessionAttribute("username");

    if ( succesLogin != null) {
      model.put("successMessage", "Session iniciada correctamente " + context.sessionAttribute("username"));
      context.sessionAttribute("succesLogin", null);
    }
    else if(successModify != null){
      model.put("successMessage", "Su perfil ha sido modificado");
      context.sessionAttribute("successModify", null);
    }

    EntityManager em = BDUtils.getEntityManager();


    if(context.sessionAttribute("tipo_persona").equals("persona_humana")) {
      TypedQuery<PersonaHumana> query = em.createQuery(
              "SELECT p FROM PersonaHumana p JOIN p.usuario u WHERE u.usuario = :usu", PersonaHumana.class);
      query.setParameter("usu", usuarioNombre);

      PersonaHumana ph = query.getSingleResult();
      model.put("tipoPersona", context.sessionAttribute("tipo_persona"));
      model.put("nombre", ph.nombre);
      model.put("apellido", ph.apellido);
      model.put("direccion", ph.getDireccion());
      model.put("fechaNacimiento", ph.getFechaDeNacimiento());
      model.put("tipo-mdc", ph.getMediosDeContacto().get(0).getTipo());
      model.put("detalle-mdc", ph.getMediosDeContacto().get(0).getDetalle());
    }else if (context.sessionAttribute("tipo_persona").equals("persona_juridica")){
      TypedQuery<PersonaJuridica> query = em.createQuery(
          "SELECT j FROM PersonaJuridica j JOIN j.usuario u WHERE u.usuario=:usu",PersonaJuridica.class);
      query.setParameter("usu", usuarioNombre);

      PersonaJuridica pj = query.getSingleResult();
      model.put("tipoPersona", context.sessionAttribute("tipo_persona"));
      model.put("nombre", pj.razonSocial);
      model.put("tipo", pj.tipo);
      model.put("direccion",pj.getDireccion());
      model.put("tipo-mdc", pj.getMediosDeContacto().get(0).getTipo());
      model.put("detalle-mdc", pj.getMediosDeContacto().get(0).getDetalle());

    }

    context.render("/templates/perfil_"+context.sessionAttribute("tipo_persona")+".mustache", model);


  }
}
