package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import java.util.List;

@Setter
@Getter
public abstract class Persona {
  private List<MedioDeContacto> mediosDeContacto;
  private String direccion;

}