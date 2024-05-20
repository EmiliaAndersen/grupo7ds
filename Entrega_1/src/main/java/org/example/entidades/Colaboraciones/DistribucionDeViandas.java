package org.example.entidades.Colaboraciones;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class DistribucionDeViandas implements Colaboracion{
  @Getter
  @Setter
  private String heladeraOrigen;
  @Getter
  @Setter
  private String heladeraDestino;
  @Getter
  @Setter
  private String motivo;
  @Getter
  @Setter
  private LocalDate fecha;
  @Override
  public void colaborar() {

  }
  public Double calcularPuntos(){return 1.0;}
}
