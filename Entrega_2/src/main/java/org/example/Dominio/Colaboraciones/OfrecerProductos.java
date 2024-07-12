package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Ofertas.Oferta;

public class OfrecerProductos extends Colaboracion{
  @Override
  public void procesarColaboracion() {
    Oferta oferta = new Oferta();
  }

  @Override
  public Double calcularPuntos() {
    return 0.0;
  }

}
