package org.example.Dominio.Documentos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.jetbrains.annotations.NotNull;

@Entity
@Table(name="documento")

public class Documento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)  // Esto asegura que no sea null en la base de datos
  @NotNull  // Esto asegura que no sea null en la aplicación (validación)
  @Getter
  @Setter
  private Integer Documento;
  @Column
  @Getter
  @Setter
  private String tipo;
  @Column
  @Getter
  @Setter
  private String genero;
  @Column
  @Getter
  @Setter
  private String sexo;

  public Documento(Integer Documento, String tipo, String genero, String sexo) {
    this.Documento = Documento;
    this.tipo = tipo;
    this.genero = genero;
    this.sexo = sexo;
  }

  public Documento() {

  }
}
