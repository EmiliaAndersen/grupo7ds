package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.MediosContacto.TipoMedioContacto;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Persona.PersonaJuridica;
import org.example.Dominio.Persona.TipoJuridica;
import org.example.Dominio.Rol.Colaborador;
import org.example.Validador.ControlPasswordDebil;
import org.example.Validador.Nist800;
import org.example.Validador.Usuario;
import org.example.Validador.Validador;

import org.example.repositorios.RepositorioUsuarios;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Random;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PostSignIn implements Handler {

  //Quiero hacer un singleton, no se si esta bien no me acuerdo como se implementaba
  RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getRepositorioUsuarios();

  public void handle(@NotNull Context context){
    var model = new HashMap<String, Object>();

  
    String usuarioNombre = context.formParam("username");
    String usuarioContraseña = context.formParam("password1");

    Validador validador = new Validador();

    validador.addFiltro(new ControlPasswordDebil());
    validador.addFiltro(new Nist800());

    if (!validador.validarPassword(usuarioContraseña)) {

      model.put("errorContraseniaDebil", "La contrasenia es debil");
      context.render("/templates/signin.mustache", model);
      return;
    }


    //Verifico si en el repositorio existe ese usuario, si no existe lo creo y redirigo al login
    // si existe lo redirigo a la misma pagina. TODO: AGREGAR ERROR PARA QUE SEPA QUE EXISTE USUARIO


    if (repoUsuarios.verificarUsuarios(usuarioNombre)){
      Usuario usuario = new Usuario( usuarioNombre, usuarioContraseña);
      repoUsuarios.addUsuario(usuario);
      instanciarPersonas(context, usuario);
      context.sessionAttribute("successSignIn", true);
      context.redirect("/login");
    }else{
      System.out.println("Usuario existe");
      context.sessionAttribute("ErrorSignIn", true);
      context.redirect("/signin");
    }

  }

  public void instanciarPersonas(Context context,Usuario usuario){
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    MedioDeContacto medioDeContacto = new MedioDeContacto();

    if (context.formParam("tipo_colaborador").equals("pf")){
      //TODO: Desharcodear el documento
      Documento documento = new Documento(123123,"asd","asdas","asd");

      PersonaHumana ph = new PersonaHumana();
      ph.setUsuario(usuario);
      ph.setDireccion(context.formParam(("direccion")));
      ph.setNombre(context.formParam("nombre"));
      ph.setApellido(context.formParam("apellido"));
      ph.fechaDeNacimiento = localDateConverter(context.formParam("fecha_nacimiento"));
      ph.cuil = context.formParam("CUIL");
      ph.setDocumento(documento);

      medioDeContacto = instanciarMDC(context);
      medioDeContacto.setPersona(ph);


      ph.getMediosDeContacto().add(medioDeContacto);


      em.persist(documento);
      em.persist(ph);
      BDUtils.commit(em);

      instanciarColaborador(context, ph);

    } else if(context.formParam("tipo_colaborador").equals("pj")){
      PersonaJuridica pj = new PersonaJuridica();
      pj.setUsuario(usuario);
      pj.razonSocial = context.formParam("razon-social");
      pj.tipo = TipoJuridica.valueOf(context.formParam("tipo-juridica"));
      pj.setDireccion(context.formParam(("direccionPJ")));

      medioDeContacto = instanciarMDCJ(context);
      medioDeContacto.setPersona(pj);

      pj.getMediosDeContacto().add(medioDeContacto);
      em.persist(pj);
      BDUtils.commit(em);

      instanciarColaborador(context, pj);
    }

  }

  private MedioDeContacto instanciarMDC(Context context) {
    MedioDeContacto mdc = new MedioDeContacto();

    try {
      System.out.println("sdfsfdsfsdfsdfsd");
      String medioContacto = context.formParam("tipo_medio_contacto").toUpperCase();
      mdc.setTipo(TipoMedioContacto.valueOf(medioContacto));
      mdc.setDetalle(context.formParam("contacto"));
    } catch (IllegalArgumentException | NullPointerException e) {
      System.out.println("Error: Medio de contacto inválido.");
    }



    return mdc;
  }
  private MedioDeContacto instanciarMDCJ(Context context) {
    MedioDeContacto mdc = new MedioDeContacto();

    try {
      String medioContacto = context.formParam("tipo_medio_contacto_pj").toUpperCase();
      mdc.setTipo(TipoMedioContacto.valueOf(medioContacto));
      mdc.setDetalle(context.formParam("contacto_pj"));
    } catch (IllegalArgumentException | NullPointerException e) {
      System.out.println("Error: Medio de contacto inválido.");
    }



    return mdc;
  }
  public LocalDate localDateConverter(String fecha){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(fecha, formatter);
  }

  public String generarCodigoAleatorio() {
    Random random = new Random();
    StringBuilder codigo = new StringBuilder(12);
    for (int i = 0; i < 12; i++) {
      int digito = random.nextInt(10); // Genera un dígito entre 0 y 9
      codigo.append(digito);
    }
    return codigo.toString();
  }

  public void instanciarColaborador(Context context, Persona persona){
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    Colaborador col = new Colaborador();
    col.setPersona(persona);
    em.persist(col);
    BDUtils.commit(em);
  }
}


