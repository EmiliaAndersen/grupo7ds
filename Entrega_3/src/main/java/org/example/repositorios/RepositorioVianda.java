package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Viandas.Vianda;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositorioVianda {

    private static RepositorioVianda instance;

    public RepositorioVianda(){}

    public static RepositorioVianda getInstance(){
        if(instance == null){
            instance = new RepositorioVianda();
        }
        return instance;
    }

    public void addVianda(Vianda vianda){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(vianda);
        BDUtils.commit(em);
    }

    public List<Vianda> obtenerViandas(){
        EntityManager em = BDUtils.getEntityManager();
        return em.createQuery("SELECT v FROM Vianda v", Vianda.class).getResultList();
    }
}
