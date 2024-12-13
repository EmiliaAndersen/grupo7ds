package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Suscripciones.Notificacion;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioNotificaciones {
  private static RepositorioNotificaciones instance;

  public RepositorioNotificaciones() {}

  public static RepositorioNotificaciones getInstance(){
    if (instance == null) {
      instance = new RepositorioNotificaciones();
    }
    return instance;
  }

  public List<Notificacion> obtenerNotificacionesActivasXUsuario(String usuario){
    EntityManager em = BDUtils.getEntityManager();

    TypedQuery<Notificacion> query = em.createQuery(
        "SELECT n FROM Notificacion n JOIN n.suscriptor s JOIN s.colaborador c JOIN c.persona p JOIN p.usuario u WHERE u.usuario = :usu and n.estaResuelta = false", Notificacion.class
    ).setParameter("usu",usuario);
    return query.getResultList();
  }

  public Notificacion buscarNotificacionPorId(Long idNotificacion) {
    EntityManager em = BDUtils.getEntityManager();

    TypedQuery<Notificacion> query = em.createQuery(
        "SELECT n FROM Notificacion n WHERE n.id = :id", Notificacion.class
    ).setParameter("id",idNotificacion);
    return query.getSingleResult();
  }

  public Boolean verificarNotificacionesXUsuario(String usuario){
    if (obtenerNotificacionesActivasXUsuario(usuario).isEmpty()){
      return false;
    }else {
      return true;
    }
  }


}
