package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Colaboradores.PersonaHumana;

public class RegistrarPersonasEnSituacionVulnerable implements Colaboracion{
  @Getter
  @Setter
  private PersonaHumana personaHumana;

  @Override
  public void colaborar() {

  }
  public Double calcularPuntos(){return 2.0;}
}
