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

  public Tecnico obtenerTecnicoXUsuario(String usuario){
    EntityManager em = BDUtils.getEntityManager();
    try {
      TypedQuery<Tecnico> query = em.createQuery(
          "SELECT t FROM Tecnico t JOIN t.persona p JOIN p.usuario u WHERE u.usuario = :usu ", Tecnico.class
      ).setParameter("usu", usuario);
      return query.getSingleResult();
    }catch (Exception e){
      return null;
    }
  }
}
