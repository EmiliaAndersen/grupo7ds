package org.example.entidades;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PersonaHumana extends Colaborador{

  // habria q agregar esto al der: {
  @Getter
  @Setter
  private String TipoDoc;

  @Getter
  @Setter
  private Integer Documento;
// }
  @Getter
  @Setter
  private String nombre;

  @Getter
  @Setter
  private String apellido;

  @Getter
  @Setter
  private LocalDate fechaDeNacimiento;





  public void realizarColaboracion(){

  }

}
