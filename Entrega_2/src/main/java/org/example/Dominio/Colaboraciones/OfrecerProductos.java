package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Ofertas.Oferta;

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
