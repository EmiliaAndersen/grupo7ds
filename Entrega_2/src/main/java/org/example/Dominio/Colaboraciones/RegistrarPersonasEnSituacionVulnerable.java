package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.PersonaVulnerable;
import org.example.Dominio.Tarjetas.Tarjeta;

public class RegistrarPersonasEnSituacionVulnerable extends Colaboracion{
  @Getter
  @Setter
  private PersonaVulnerable personaVulnerable;
  @Getter
  @Setter
  private Colaborador colaborador;

  @Override
  public void procesarColaboracion() {
    PersonaVulnerable personaVulnerableRegistrada = new PersonaVulnerable();
    Tarjeta tarjeta = new Tarjeta(personaVulnerableRegistrada);
    personaVulnerableRegistrada.setTarjeta(tarjeta);
    this.personaVulnerable = personaVulnerableRegistrada;
    //this.colaborador = colaborador;
  }
  public Double calcularPuntos(){return 2.0;}
}
