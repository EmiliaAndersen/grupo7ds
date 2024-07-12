package org.example.Dominio.Colaboraciones;

import lombok.Setter;
import org.example.Dominio.Viandas.Vianda;

import java.util.List;

public class DonacionDeVianda extends Colaboracion{
  @Setter
  private List<Vianda> viandas;

  @Override
  public void procesarColaboracion() {

  }
  public Double calcularPuntos(){return viandas.size() *1.5;}
}
