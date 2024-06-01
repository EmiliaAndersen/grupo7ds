package org.example.entidades;

import lombok.Getter;
import lombok.Setter;

public class Documento {
  @Getter
  @Setter
  private Integer Documento;
  @Getter
  @Setter
  private String tipo;
  @Getter
  @Setter
  private String genero;
  @Getter
  @Setter
  private String sexo;

  public Documento(Integer Documento, String tipo, String genero, String sexo) {
    this.Documento = Documento;
    this.tipo = tipo;
    this.genero = genero;
    this.sexo = sexo;
  }
}
