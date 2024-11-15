package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;

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
}
