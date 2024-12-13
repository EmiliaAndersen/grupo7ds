package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.Incidente;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioIncidente {
    private static RepositorioIncidente instance;

    public RepositorioIncidente() {}

    public static RepositorioIncidente getInstance(){
        if (instance == null) {
            instance = new RepositorioIncidente();
        }
        return instance;
    }

    public void addIncidente(Incidente incidente){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(incidente);
        BDUtils.commit(em);
    }

    public List<Incidente> obtenerIncidentes(){
        EntityManager em = BDUtils.getEntityManager();

        TypedQuery<Incidente> query = em.createQuery(
            "SELECT i FROM Incidente i", Incidente.class
        );

        return query.getResultList();
    }

    public List<Incidente> obtenerIncidentesSinTecnico() {
        EntityManager em = BDUtils.getEntityManager();

        TypedQuery<Incidente> query = em.createQuery(
            "SELECT i FROM Incidente i WHERE i.tecnico IS NULL",
            Incidente.class
        );

        return query.getResultList();
    }

    public List<Incidente> obtenerIncidentesXTecnicos(Long id){
        EntityManager em = BDUtils.getEntityManager();

        TypedQuery<Incidente> query = em.createQuery(
            "SELECT i FROM Incidente i JOIN i.tecnico t WHERE t.id = :id", Incidente.class
        ).setParameter("id",id);

        return query.getResultList();
    }

  public Incidente obtenerIncidenteXID(Long id) {
        EntityManager em = BDUtils.getEntityManager();

        TypedQuery<Incidente> query = em.createQuery(
            "SELECT i FROM Incidente i WHERE i.id = :id", Incidente.class
        ).setParameter("id",id);

        return query.getSingleResult();
  }

  public List<Incidente> obtenerIncidentesActivosXHeladera(Heladera heladera) {
    EntityManager em = BDUtils.getEntityManager();
    TypedQuery<Incidente> query = em.createQuery(
        "SELECT i FROM Incidente i JOIN i.heladera h WHERE h.id = :id AND i.estaActiva = true",
        Incidente.class
    );
    query.setParameter("id", heladera.getId());
    return query.getResultList();
  }
}
