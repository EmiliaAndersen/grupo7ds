package org.example.entidades.Colaboraciones;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class DonacionDeDinero implements Colaboracion{
  @Getter
  @Setter
  private LocalDate fecha;
  @Getter
  @Setter
  private Double monto;
  @Getter
  @Setter
  private LocalDate frecuencia;
  @Override
  public void colaborar() {

  }
  public Double calcularPuntos(){return monto*0.5;}
}
