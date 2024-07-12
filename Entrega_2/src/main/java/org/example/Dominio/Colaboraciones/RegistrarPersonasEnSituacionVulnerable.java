package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Persona.PersonaHumana;

public class RegistrarPersonasEnSituacionVulnerable extends Colaboracion{
  @Getter
  @Setter
  private PersonaHumana personaHumana;

  @Override
  public void procesarColaboracion() {

  }
  public Double calcularPuntos(){return 2.0;}
}
