package org.example.entidades.Colaboraciones;

import org.example.entidades.Oferta;

public class OfrecerProductos implements Colaboracion{
  @Override
  public void colaborar() {
    Oferta oferta = new Oferta();
  }

  @Override
  public Double calcularPuntos() {
    return 0.0;
  }

}
