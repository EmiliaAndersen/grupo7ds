package org.example.repositorios;

import lombok.Getter;
import lombok.Setter;
import org.example.BDUtils;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Colaboraciones.DonacionDeVianda;
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



    public void addDonacionVianda(Colaboracion donacionDeVianda){
        EntityManager em = BDUtils.getEntityManager();
        BDUtils.comenzarTransaccion(em);
        em.persist(donacionDeVianda);
        BDUtils.commit(em);
    }
}
