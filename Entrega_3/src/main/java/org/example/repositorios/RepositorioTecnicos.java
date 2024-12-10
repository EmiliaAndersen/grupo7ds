package org.example.repositorios;

import org.example.BDUtils;
import org.example.Dominio.Rol.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioTecnicos {

  private static RepositorioTecnicos instance;

  public void RepositorioTecnicos() {}
  public static RepositorioTecnicos getInstance() {
    if (instance == null) {
      instance = new RepositorioTecnicos();
    }
    return instance;
  }

  public List<Tecnico> obtenerTecnicos(){
    EntityManager em = BDUtils.getEntityManager();

    TypedQuery<Tecnico> query = em
        .createQuery("SELECT t FROM Tecnico t ",Tecnico.class);

    return query.getResultList();
  }

}
