package org.example.Dominio.Rol;

import org.example.Dominio.Tarjetas.TarjetaDistribuidor;

import java.util.List;

public class Distribuidor extends Colaborador{
  public List<AperturaHeladera> aperturas;
  private TarjetaDistribuidor tarjetaDistribuidor;

  public void registrarApertura(AperturaHeladera apertura){
    aperturas.add(apertura);
  }

}
