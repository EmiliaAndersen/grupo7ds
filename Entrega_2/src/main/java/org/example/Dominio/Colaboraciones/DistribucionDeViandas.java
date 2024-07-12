package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;

import java.time.LocalDate;

public class DistribucionDeViandas extends Colaboracion{
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
  public void procesarColaboracion() {

  }
  public Double calcularPuntos(){return 1.0;}
}
