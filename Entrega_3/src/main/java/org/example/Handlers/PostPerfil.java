package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Persona.PersonaJuridica;
import org.example.Dominio.Persona.TipoJuridica;
import org.example.Validador.Usuario;
import org.example.repositorios.RepositorioUsuarios;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import org.example.BDUtils;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PostPerfil implements Handler {

  //Quiero hacer un singleton, no se si esta bien no me acuerdo como se implementaba
  RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getRepositorioUsuarios();

  public void handle(@NotNull Context context){

    var model = new HashMap<String, Object>();



    String accion = context.formParam("btn-accion");
    if(accion.equals("save")){
      actualizarUsuario(context);
      context.sessionAttribute("successModify", true);
      context.redirect("/perfil_"+context.sessionAttribute("tipo_persona"));
    }else if(accion.equals("logout")){
      context.sessionAttribute("username", null);
      context.redirect("/login");
    }else{
        context.redirect("/perfil_"+context.sessionAttribute("tipo_persona"));
    }

  }

  private void actualizarUsuario(Context context) {
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    if(context.sessionAttribute("tipo_persona").equals("persona_humana")) {
      TypedQuery<PersonaHumana> query = em.createQuery(
              "SELECT p FROM PersonaHumana p JOIN p.usuario u WHERE u.usuario = :usu", PersonaHumana.class);
      String usuarioNombre = context.sessionAttribute("username");
      query.setParameter("usu", usuarioNombre);

      PersonaHumana ph = query.getSingleResult();

      String dateString = context.formParam("fecha-nacimiento");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate loc = LocalDate.parse(dateString, formatter);

      ph.nombre = context.formParam("nombre");
      ph.apellido = context.formParam("apellido");
      ph.setDireccion(context.formParam("direccion"));
      ph.setFechaDeNacimiento(loc);
      em.getTransaction().commit(); // Confirma la transacción para aplicar los cambios
      System.out.println(ph.nombre);
    }else if(context.sessionAttribute("tipo_persona").equals("persona_juridica")){
      TypedQuery<PersonaJuridica> query = em.createQuery(
              "SELECT p FROM PersonaJuridica p JOIN p.usuario u WHERE u.usuario = :usu", PersonaJuridica.class
      );
      String usuarioNombre = context.sessionAttribute("username");
      query.setParameter("usu", usuarioNombre);

      PersonaJuridica pj = query.getSingleResult();

      pj.razonSocial = context.formParam("razon-social");
      pj.setDireccion(context.formParam("direccion"));
      em.getTransaction().commit();
      System.out.println(pj.razonSocial);
    }
    else{
      System.out.println("Error");
    }
  }

}
