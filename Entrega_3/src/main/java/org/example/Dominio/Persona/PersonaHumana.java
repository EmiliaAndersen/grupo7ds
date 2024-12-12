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
  @Getter
  public String nombre;
  @Column
  @Setter
  public String apellido;
  @Column
  @Setter
  public LocalDate fechaDeNacimiento;

  @PrePersist
  @PreUpdate
    private void validarFechaDeNacimiento() {
        if (fechaDeNacimiento != null) {
            // No permitir fechas de nacimiento mayores a 150 años
            LocalDate fechaLimite = LocalDate.now().minusYears(150);
            if (fechaDeNacimiento.isBefore(fechaLimite)) {
                throw new IllegalArgumentException("La fecha de nacimiento no puede ser mayor a 150 años.");
            }
            // No permitir fechas en el futuro
            if (fechaDeNacimiento.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("La fecha de nacimiento no puede ser una fecha futura.");
            }
        }
    }



  @Column
  @Setter
  public String cuil;

  public PersonaHumana() {
    this.setMediosDeContacto(new ArrayList<>());
  }

}
