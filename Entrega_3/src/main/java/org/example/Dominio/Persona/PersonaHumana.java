package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PersonaHumana extends Persona {


  public org.example.Dominio.Documentos.Documento Documento;
  public String nombre;
  public String apellido;
  public LocalDate fechaDeNacimiento;
  public String cuil;

}
