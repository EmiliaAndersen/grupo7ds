package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Incidente;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioHeladeras {

    private static RepositorioHeladeras instance;

    public RepositorioHeladeras() {}

    public static RepositorioHeladeras getInstance(){
        if (instance == null) {
            instance = new RepositorioHeladeras();
        }
        return instance;
    }

    public Heladera obtenerHeladera(String heladeraId) {
        EntityManager em = BDUtils.getEntityManager();
        try {
            TypedQuery<Heladera> query = em.createQuery("SELECT h FROM Heladera h WHERE h.id = :heladeraId", Heladera.class);
            query.setParameter("heladeraId", Long.valueOf(heladeraId));

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Heladera> obtenerTodasHeladeras(){
        EntityManager em = BDUtils.getEntityManager();

        List<Heladera> heladeras = em.createQuery("SELECT h FROM Heladera h", Heladera.class).getResultList();
        return heladeras;
    }

    public Heladera obtenerHeladeraXIncidente(Incidente incidente) {
        EntityManager em = BDUtils.getEntityManager();

        TypedQuery<Heladera> query = em.createQuery(
            "SELECT h FROM Incidente i JOIN i.heladera h WHERE i.id = :id", Heladera.class
        ).setParameter("id",incidente.getId());
        return query.getSingleResult();
    }

  public List<Heladera> obtenerHeladerasDisponibles(Integer viandas) {
      EntityManager em = BDUtils.getEntityManager();

      TypedQuery<Heladera> query = em.createQuery(
          "SELECT h FROM Heladera h WHERE h.capacidad - h.stock > :viandas", Heladera.class
      ).setParameter("viandas",viandas);
      return query.getResultList();
  }
}
