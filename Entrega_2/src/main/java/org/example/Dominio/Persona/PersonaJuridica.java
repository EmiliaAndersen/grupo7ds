package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;

public class PersonaJuridica extends Persona {
  @Getter
  @Setter
  private String razonSocial;
  @Getter
  @Setter
  private TipoJuridica tipo;
  @Getter
  @Setter
  private String rubro;


  public void realizarColaboracion(){

  }
}
