package org.example.entidades;

import lombok.Getter;
import lombok.Setter;

public class Oferta {
  @Getter
  private String nombre;
  @Getter
  private Double puntosNecesarios;
  @Getter
  private PersonaJuridica personaJuridica;
}
