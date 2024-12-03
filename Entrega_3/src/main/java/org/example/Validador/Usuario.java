package org.example.Validador;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.jetbrains.annotations.NotNull;

@Entity
@Table(name="usuario")
public class Usuario {

  public Usuario( String usuario, String contrasenia) {
    this.usuario = usuario;
    this.contrasenia = contrasenia;
  }
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)  // Esto asegura que no sea null en la base de datos
  @NotNull  // Esto asegura que no sea null en la aplicación (validación)
  @Getter
  @Setter
  private String usuario;

  @Column(nullable = false)  // Esto asegura que no sea null en la base de datos
  @NotNull  // Esto asegura que no sea null en la aplicación (validación)
  @Getter
  @Setter
  private String contrasenia;


  public Usuario() {

  }
}
