package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AperturaHeladera {

  @Getter
  @Setter
  private Heladera heladera;

  @Getter
  @Setter
  private LocalDateTime fechaYHora;
}
