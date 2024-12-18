package org.example.Dominio.Notificador;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.MediosContacto.TipoMedioContacto;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Suscripciones.Notificacion;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.Validador.Usuario;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioSuscriptores;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class Notificador {
  private static Notificador instance;

  public void Notificador() {}
  public static Notificador getInstance() {
    if (instance == null) {
      instance = new Notificador();
    }
    return instance;
  }


  public void movimientoHeladera(Heladera heladera, TipoSuscripcion tipoSuscripcion){
    RepositorioSuscriptores repositorioSuscriptores = RepositorioSuscriptores.getInstance();
    switch (tipoSuscripcion){
      case FALTANTES:
        List<Suscriptor> suscriptores = repositorioSuscriptores.obtenerSuscriptoresDeHeladera(heladera,tipoSuscripcion);
        for (Suscriptor suscriptor : suscriptores){
          if (suscriptor.getNumeroAviso() >= (heladera.getCapacidad() - heladera.getStock())) {
              generarNotificacion(suscriptor,"Faltan menos de " + suscriptor.getNumeroAviso() + " para que se llene la heladera",tipoSuscripcion,heladera);
          }
        }
        break;
      case RESTANTES:
        List<Suscriptor> suscriptoresRestantes = repositorioSuscriptores.obtenerSuscriptoresDeHeladera(heladera,tipoSuscripcion);
        for (Suscriptor suscriptor : suscriptoresRestantes){
          if (suscriptor.getNumeroAviso() >= heladera.getStock()) {
            generarNotificacion(suscriptor,"Quedan menos de  " + suscriptor.getNumeroAviso() + " en la heladera", tipoSuscripcion,heladera);
          }
        }
        break;
      case DESPERFECTO:
        List<Suscriptor> suscriptoresDesperfecto = repositorioSuscriptores.obtenerSuscriptoresDeHeladera(heladera,tipoSuscripcion);
        for (Suscriptor suscriptor : suscriptoresDesperfecto) {
          generarNotificacion(suscriptor, "Hubo un desperfecto en la heladera", tipoSuscripcion, heladera);
        }
        break;
    }
  }

  private void generarNotificacion(Suscriptor suscriptor, String s, TipoSuscripcion tipoSuscripcion,Heladera heladera) {
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    Notificacion notificacion = new Notificacion();
    notificacion.setEstaResuelta(false);
    notificacion.setTipo(tipoSuscripcion);
    notificacion.setHeladera(heladera);
    notificacion.setSuscriptor(suscriptor);
    notificacion.setMensajeEnviado(s);
    em.persist(notificacion);
    BDUtils.commit(em);

    RepositorioSuscriptores repositorioSuscriptores = RepositorioSuscriptores.getInstance();
    String urlString = "https://deltaeco.com.ar/v3/whatsapp/?";
    MedioDeContacto medioDeContacto = repositorioSuscriptores.obtenerMDCXSus(suscriptor);

    TypedQuery<Usuario> query = em.createQuery(
        "SELECT u FROM Suscriptor s JOIN s.colaborador c JOIN c.persona p JOIN p.usuario u WHERE s.id = :id", Usuario.class
    ).setParameter("id",suscriptor.getId());
    Usuario usuario = query.getSingleResult();
    String nombre = usuario.getUsuario();

    try {
    if(medioDeContacto.getTipo().equals(TipoMedioContacto.WHATSAPP)){

      urlString += "phone=" + URLEncoder.encode(medioDeContacto.getDetalle(), StandardCharsets.UTF_8)
          + "&msj_heladera=" + URLEncoder.encode(s, StandardCharsets.UTF_8)
          + "&usr_heladera=" + URLEncoder.encode(nombre, StandardCharsets.UTF_8)
          + "&heladera=" + URLEncoder.encode(heladera.getUbicacion().getNombre(), StandardCharsets.UTF_8);
    }else if(medioDeContacto.getTipo().equals(TipoMedioContacto.CORREO_ELECTRONICO)){
      urlString += "mail=" + URLEncoder.encode(medioDeContacto.getDetalle(), StandardCharsets.UTF_8)
          + "&msj_heladera=" + URLEncoder.encode(s, StandardCharsets.UTF_8)
          + "&usr_heladera=" + URLEncoder.encode(nombre, StandardCharsets.UTF_8)
          + "&heladera=" + URLEncoder.encode(heladera.getUbicacion().getNombre(), StandardCharsets.UTF_8);

    }

      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      int responseCode = connection.getResponseCode();
      connection.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}


