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

  @Getter
  @Setter
  private Double cantidad;


  public RegistrarPersonasEnSituacionVulnerable(PersonaVulnerable per){
    this.personaVulnerable=per;
   
  }
  public RegistrarPersonasEnSituacionVulnerable(){
  }
  @Override
  public void procesarColaboracion(Colaborador colaborador) {
      boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_HUMANA);
      if (puedoProcesar) {
          Persona persona = personaVulnerable.getPersona();
          String direccion = persona.getDireccion();
      }
  }



  public Double calcularPuntos(){return 2.0;}
}
