package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

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

  public PersonaJuridica() {
    this.setMediosDeContacto(new ArrayList<>());
  }
}
