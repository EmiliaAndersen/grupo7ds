package org.example.repositorios;

import lombok.Getter;
import lombok.Setter;
import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.DonacionDeVianda;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Viandas.Vianda;
import org.example.Validador.Usuario;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RepositorioColaboraciones {
    private static RepositorioColaboraciones instance;

    public RepositorioColaboraciones() {}

    public static RepositorioColaboraciones getInstance(){
        if (instance == null) {
            instance = new RepositorioColaboraciones();
        }
        return instance;
    }

    @Getter
    @Setter
    public List<Colaboracion> colaboraciones = new ArrayList<>();



    public void addDonacionVianda( Colaboracion donacionDeVianda, Vianda vianda){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(vianda);
        em.persist(donacionDeVianda);
        BDUtils.commit(em);
    }

    public void addDonacionDinero(Colaboracion donacionDeDinero){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(donacionDeDinero);
        BDUtils.commit(em);
    }

    public void addDistribucionVianda(Colaboracion distribucionVianda){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(distribucionVianda);
        BDUtils.commit(em);
    }

    public void addHacerseCargoHeladera(Colaboracion hacerseCargoHeladera, PuntoEstrategico pto, Heladera heladera){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(pto);
        em.persist(heladera);
        em.persist(hacerseCargoHeladera);
        BDUtils.commit(em);
    }

    public void addOfrecerProducto(Colaboracion ofrecerProducto){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(ofrecerProducto);
        BDUtils.commit(em);
    }
}
