package org.example.entidades;

import lombok.Getter;

public class Tecnico {
  @Getter
  private String nombre;
  @Getter
  private String apellido;
  @Getter
  private Documento documento;
  @Getter
  private String cuil;
  @Getter
  private MedioDeContacto medioDeContacto;

}
