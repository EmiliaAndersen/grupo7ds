package org.example.Dominio.Tarjetas;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class SolicitudPermisos {
  @Getter
  @Setter
  private LocalDateTime fechaYHora; //fecha y hora actual

  public SolicitudPermisos(LocalDateTime fechaYHora){
    this.fechaYHora = fechaYHora;
  }
}
