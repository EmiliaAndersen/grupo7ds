package org.example.entidades.Colaboraciones;

import java.util.List;

public class DonacionDeVianda implements Colaboracion{
  private List<String> viandas; //TODO: Agregar las viandas

  @Override
  public void colaborar() {

  }
  public Double calcularPuntos(){return viandas.size() *1.5;}
}
