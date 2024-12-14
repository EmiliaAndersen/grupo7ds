package org.example.Dominio.Notificador;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Suscripciones.Notificacion;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.repositorios.RepositorioHeladeras;
import org.example.repositorios.RepositorioSuscriptores;

import javax.persistence.EntityManager;
import java.util.List;

public final class Notificador {
  private static Notificador instance;

  public void RepositorioTecnicos() {}
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
  }

}


