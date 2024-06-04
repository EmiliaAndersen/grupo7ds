package org.example.Dominio.MediosContacto;

import lombok.Getter;

public class MedioDeContacto {
  @Getter
  private TipoMedioContacto tipo;
  @Getter
  private String detalle;
}
