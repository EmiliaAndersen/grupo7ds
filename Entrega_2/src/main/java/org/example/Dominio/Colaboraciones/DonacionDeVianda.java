package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Viandas.Vianda;

import java.util.List;

public class DonacionDeVianda extends Colaboracion{
  private List<Vianda> viandas; //TODO: Agregar las viandas

  @Override
  public void procesarColaboracion() {

  }
  public Double calcularPuntos(){return viandas.size() *1.5;}
}
