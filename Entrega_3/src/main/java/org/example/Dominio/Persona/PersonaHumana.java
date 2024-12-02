package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Documentos.Documento;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("persona_humana")
@Setter
@Getter
public class PersonaHumana extends Persona {

  @OneToOne
  @JoinColumn(name = "documento_id", referencedColumnName = "id", unique = true, nullable = true)
  public Documento Documento;

  @Column
  @Setter
  public String nombre;
  @Column
  @Setter
  public String apellido;
  @Column
  @Setter
  public LocalDate fechaDeNacimiento;
  @Column
  @Setter
  public String cuil;

  public PersonaHumana() {
    this.setMediosDeContacto(new ArrayList<>());
  }

}
