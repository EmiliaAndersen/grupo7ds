package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Tarjetas.TarjetaDistribuidor;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioTarjetasDist {

    private static RepositorioTarjetasDist instance;

    public RepositorioTarjetasDist() {}

    public static RepositorioTarjetasDist getInstance(){
        if (instance == null) {
            instance = new RepositorioTarjetasDist();
        }
        return instance;
    }

    public void addTarjeta(TarjetaDistribuidor tarjeta){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(tarjeta);
        BDUtils.commit(em);
    }

    public List<TarjetaDistribuidor> obtenerTarjeta(){
        EntityManager em = BDUtils.getEntityManager();

        TypedQuery<TarjetaDistribuidor> query = em.createQuery(
                "SELECT t FROM TarjetaDistribuidor t", TarjetaDistribuidor.class
        );

        return query.getResultList();
    }

    public TarjetaDistribuidor obtenerTarjetaId(Long id){
        EntityManager em = BDUtils.getEntityManager();
        try {
            TypedQuery<TarjetaDistribuidor> query = em.createQuery("SELECT t FROM TarjetaDistribuidor t WHERE t.id = :id", TarjetaDistribuidor.class);
            query.setParameter("id", Long.valueOf(id));

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
