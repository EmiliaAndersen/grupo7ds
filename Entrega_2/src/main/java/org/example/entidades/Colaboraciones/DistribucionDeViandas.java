package org.example.entidades.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.entidades.Heladeras.Heladera;

import java.time.LocalDate;

public class DistribucionDeViandas implements Colaboracion{
  @Getter
  @Setter
  private Heladera heladeraOrigen;
  @Getter
  @Setter
  private Heladera heladeraDestino;
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
