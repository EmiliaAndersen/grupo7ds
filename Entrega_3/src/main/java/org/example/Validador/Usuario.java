package org.example.Validador;

import lombok.Getter;
import lombok.Setter;

public class Usuario {

  public Usuario(String usuario, String contrasenia) {
    this.usuario = usuario;
    this.contrasenia = contrasenia;
  }

  @Getter
  @Setter
  private String usuario;

  @Getter
  @Setter
  private String contrasenia;
}
