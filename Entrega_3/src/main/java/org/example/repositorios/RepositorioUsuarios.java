package org.example.repositorios;

import lombok.Getter;
import lombok.Setter;
import org.example.Servicio.LocalizadorTecnicos;
import org.example.Validador.Usuario;

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
    usuarios.add(usuario);
  }

  public boolean verificarUsuarios(String nombre){
    System.out.println("Verificando usuario...");
    return  !usuarios.stream().anyMatch(usuario -> usuario.getUsuario().equals(nombre));
  }

  public boolean verificarUsuarioYcontrasena(String nombre, String contrasena){
    return  usuarios.stream().anyMatch(usuario -> usuario.getUsuario().equals(nombre)) && usuarios.stream().anyMatch(usuario -> usuario.getContrasenia().equals(contrasena));
  }

}
