package org.example.entidades.Colaboraciones;

import org.example.entidades.Viandas.Vianda;

import java.util.List;

public class DonacionDeVianda implements Colaboracion{
  private List<Vianda> viandas; //TODO: Agregar las viandas

  @Override
  public void colaborar() {

  }
  public Double calcularPuntos(){return viandas.size() *1.5;}
}
