package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("persona_juridica")
@Setter
@Getter
public class PersonaJuridica extends Persona {

  @Column
  @Setter
  public String razonSocial;
  @Column
  @Setter
  public TipoJuridica tipo;

}
