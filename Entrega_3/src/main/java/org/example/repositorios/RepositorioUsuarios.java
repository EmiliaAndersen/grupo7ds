package org.example.repositorios;

import lombok.Getter;
import lombok.Setter;
import org.example.BDUtils;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Servicio.LocalizadorTecnicos;
import org.example.Validador.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {

  private static RepositorioUsuarios instance;

  public RepositorioUsuarios() {}

  public static RepositorioUsuarios getRepositorioUsuarios(){
    if (instance == null) {
      instance = new RepositorioUsuarios();
    }
    return instance;
  }

  @Getter
  @Setter
  public List<Usuario> usuarios = new ArrayList<>();



  public void addUsuario(Usuario usuario){
    EntityManager em = BDUtils.getEntityManager();
    BDUtils.comenzarTransaccion(em);
    em.persist(usuario);
    BDUtils.commit(em);
  }

  public boolean verificarUsuarios(String nombre){
    EntityManager em = BDUtils.getEntityManager();
    //TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usu",Usuario.class);ARA TRAER TODO
    TypedQuery<String> query = em.createQuery("SELECT u.usuario FROM Usuario u WHERE u.usuario = :usu", String.class);
    query.setParameter("usu", nombre);
    List result = query.getResultList();

    return result.isEmpty();
    //System.out.println("Verificando usuario...");
    //return  !usuarios.stream().anyMatch(usuario -> usuario.getUsuario().equals(nombre));
  }

  public boolean verificarUsuarioYcontrasena(String nombre, String contrasena){
    EntityManager em = BDUtils.getEntityManager();
    return !em.createQuery("SELECT u from Usuario u WHERE u.usuario = :usu and u.contrasenia = :con").setParameter("usu",nombre).setParameter("con",contrasena).getResultList().isEmpty();
  }

  public List<PersonaHumana> getPersonasHumanas() {
    EntityManager em = BDUtils.getEntityManager();
    TypedQuery<PersonaHumana> query = em.createQuery("SELECT ph FROM PersonaHumana ph",PersonaHumana.class);
    List<PersonaHumana> personaHumanas = query.getResultList();
    return personaHumanas;
  }
}
