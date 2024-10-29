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

public class PostPerfil implements Handler {

  //Quiero hacer un singleton, no se si esta bien no me acuerdo como se implementaba
  RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getRepositorioUsuarios();

  public void handle(@NotNull Context context){

    var model = new HashMap<String, Object>();

    String accion = context.formParam("btn-accion");
    if(accion.equals("save")){
      //Usuario usuario = repoUsuarios.obtenerUsuarioPorNombre(usuarioNombre);
      model.put( "successMessage", "Datos guardados correctamente " + context.sessionAttribute("username"));
      context.render("/templates/perfil.mustache", model);
    }else if(accion.equals("logout")){
      context.sessionAttribute("username", null);
      context.redirect("/login");
    }else{
      context.redirect("/perfil");
    }

  }

}
