package org.example.Validador;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
public class Usuario {

  public Usuario( String usuario, String contrasenia) {
    this.usuario = usuario;
    this.contrasenia = contrasenia;
  }
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  @Getter
  @Setter
  private String usuario;

  @Column
  @Getter
  @Setter
  private String contrasenia;


  public Usuario() {

  }
}
