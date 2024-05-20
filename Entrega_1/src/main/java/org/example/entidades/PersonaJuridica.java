package org.example.entidades;

import lombok.Getter;
import lombok.Setter;

public class PersonaJuridica extends Colaborador{
  @Getter
  @Setter
  private String razonSocial;
  @Getter
  @Setter
  private TipoEmpresa tipo;
  @Getter
  @Setter
  private String rubro;


  public void realizarColaboracion(){

  }
}
