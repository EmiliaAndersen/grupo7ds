package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.Incidente;

import javax.persistence.EntityManager;

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

}
