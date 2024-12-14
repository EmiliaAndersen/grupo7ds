package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Tecnico;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioSuscriptores {
  private static RepositorioSuscriptores instance;

  public void RepositorioSuscriptores() {}
  public static RepositorioSuscriptores getInstance() {
    if (instance == null) {
      instance = new RepositorioSuscriptores();
    }
    return instance;
  }

  public List<Suscriptor> obtenerSuscriptoresDeHeladera(Heladera heladera, TipoSuscripcion tipoSuscripcion){
    EntityManager em = BDUtils.getEntityManager();

    TypedQuery<Suscriptor> query = em
        .createQuery("SELECT s FROM Suscriptor s JOIN s.heladera h WHERE h.id = :id AND s.tipo = :tipo",Suscriptor.class)
        .setParameter("id",heladera.getId())
        .setParameter("tipo",tipoSuscripcion);

    return query.getResultList();
  }
}
