package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import java.util.List;

public abstract class Persona {
  @Getter
  @Setter
  private List<MedioDeContacto> mediosDeContacto;
  @Getter
  @Setter
  private String direccion;
}