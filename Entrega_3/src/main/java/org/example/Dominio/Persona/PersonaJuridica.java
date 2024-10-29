package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("persona_juridica")
public class PersonaJuridica extends Persona {

  @Column
  public String razonSocial;
  @Column
  public TipoJuridica tipo;

}
