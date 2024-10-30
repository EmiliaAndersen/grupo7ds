package org.example.repositorios;

import lombok.Getter;
import lombok.Setter;
import org.example.BDUtils;
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
    String name = query.getSingleResult();

    return !name.isEmpty(); // Si no está vacío, el usuario existe

    //System.out.println("Verificando usuario...");
    //return  !usuarios.stream().anyMatch(usuario -> usuario.getUsuario().equals(nombre));
  }

  public boolean verificarUsuarioYcontrasena(String nombre, String contrasena){
    return  usuarios.stream().anyMatch(usuario -> usuario.getUsuario().equals(nombre)) && usuarios.stream().anyMatch(usuario -> usuario.getContrasenia().equals(contrasena));
  }

}
