package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Rol.PersonaVulnerable;

import javax.persistence.*;

@Entity
@Table(name = "registrar_persona_vulnerable")
public class RegistrarPersonasEnSituacionVulnerable extends Colaboracion{



  @OneToOne
  @JoinColumn(name = "persona_vulnearble_id", referencedColumnName = "id")
  @Getter
  @Setter
  private PersonaVulnerable personaVulnerable;

  @Override
  public void procesarColaboracion(Colaborador colaborador) {
      boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_HUMANA);
      if (puedoProcesar) {
          Persona persona = personaVulnerable.getPersona();
          String direccion = persona.getDireccion();
          //Enviar tarjetas a esa direccion y ver de dar de alta
          //Ver si ya esta generada o la debo generar //PersonaVulnerable personaVulnerableRegistrada = new PersonaVulnerable();
          // Tarjeta tarjeta = new Tarjeta(personaVulnerableRegistrada);
          // personaVulnerableRegistrada.setTarjeta(tarjeta);
          // this.personaVulnerable = personaVulnerableRegistrada;
          //this.colaborador = colaborador;
      }
  }



  public Double calcularPuntos(){return 2.0;}
}
