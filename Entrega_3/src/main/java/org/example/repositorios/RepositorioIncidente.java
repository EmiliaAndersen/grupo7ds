package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.Incidente;

import javax.persistence.EntityManager;
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
        return em.createQuery("SELECT i FROM Incidente i", Incidente.class).getResultList();
    }

}
