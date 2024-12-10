package org.example.Handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.BDUtils;
import org.example.Dominio.AreaDeCobertura.AreaCobertura;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.MediosContacto.TipoMedioContacto;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Tecnico;
import org.example.Validador.Usuario;
import org.example.repositorios.RepositorioUsuarios;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.HashMap;

public class postBackofficeTecnicos implements Handler {
  @Override
  public void handle(@NotNull Context context) throws Exception {
    var model = new HashMap<String, Object>();

    RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getRepositorioUsuarios();
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    try {
      String usuario = context.formParam("username");
      String contrasenia = context.formParam("password1");
      if (repositorioUsuarios.verificarUsuarios(usuario)){
        generarPersonaHumana(context);

      }else {
        model.put("errorMessage","El nombre de usuario o contraseña ya existe");
      }
    } catch (Exception e){
      model.put("errorMessage","Error al crear Tecnico");
    }

    context.render("/templates/backofficeTecnicos.mustache",model);
  }

  private void generarPersonaHumana(Context context) {
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);

    String usuario = context.formParam("username");
    String contrasenia = context.formParam("password1");

    Usuario usuario1 = new Usuario(usuario,contrasenia);
    em.persist(usuario1);

    String nombre = context.formParam("nombre");
    String apellido = context.formParam("apellido");
    String tipoDocumento = context.formParam("tipo_documento");
    String numeroDocumento = context.formParam("numero_documento");
    //String cuil = context.formParam("cuil");
    String tipo_medio_contacto = context.formParam("tipo_medio_contacto");
    String contacto = context.formParam("contacto");
    String latitud = context.formParam("latitud");
    String longitud = context.formParam("longitud");
    String radio = context.formParam("radio");

    PersonaHumana personaHumana = new PersonaHumana();
    personaHumana.setNombre(nombre);
    personaHumana.setApellido(apellido);

    Documento documento = new Documento(Integer.parseInt(numeroDocumento),tipoDocumento, "", "");
    em.persist(documento);

    personaHumana.setDocumento(documento);
    personaHumana.setCuil(numeroDocumento);

    MedioDeContacto medioDeContacto = new MedioDeContacto();
    medioDeContacto.setTipo(TipoMedioContacto.valueOf(tipo_medio_contacto));
    medioDeContacto.setDetalle(contacto);
    medioDeContacto.setPersona(personaHumana);


    personaHumana.getMediosDeContacto().add(medioDeContacto);


    AreaCobertura areaCobertura = new AreaCobertura();
    areaCobertura.setLatitudCentro(Double.parseDouble(latitud));
    areaCobertura.setLongitudCentro(Double.parseDouble(longitud));
    areaCobertura.setRadio(Double.parseDouble(radio));
    em.persist(areaCobertura);

    Tecnico tecnico = new Tecnico();
    tecnico.setPersona(personaHumana);
    tecnico.setAreaCobertura(areaCobertura);
    em.persist(tecnico);


    personaHumana.asignarRol(personaHumana,tecnico);
    personaHumana.setDireccion("");

    em.persist(personaHumana);
    BDUtils.commit(em);
  }

}

