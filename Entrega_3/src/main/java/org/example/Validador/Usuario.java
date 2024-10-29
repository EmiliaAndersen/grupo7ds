package org.example.Validador;

import lombok.Getter;
import lombok.Setter;

public class Usuario {

  public Usuario(String id, String usuario, String contrasenia) {
    this.usuario = usuario;
    this.id = id;
    this.contrasenia = contrasenia;
  }

  @Getter
  @Setter
  private String usuario;

  @Getter
  @Setter
  private String contrasenia;

  @Getter
  @Setter
  private String id;
}
