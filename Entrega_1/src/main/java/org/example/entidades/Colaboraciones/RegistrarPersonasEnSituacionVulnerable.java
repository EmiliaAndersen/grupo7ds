package org.example.entidades.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.entidades.PersonaHumana;

public class RegistrarPersonasEnSituacionVulnerable implements Colaboracion{
  @Getter
  @Setter
  private PersonaHumana personaHumana;

  @Override
  public void colaborar() {

  }
  public Double calcularPuntos(){return 2.0;}
}
