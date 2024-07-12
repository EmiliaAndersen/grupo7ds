package org.example.Dominio.MediosContacto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedioDeContacto {
  private TipoMedioContacto tipo;
  private String detalle;
}
