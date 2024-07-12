package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Ofertas.Oferta;
import org.example.Dominio.Rol.Colaborador;

public class OfrecerProductos extends Colaboracion{
  @Override
  public void procesarColaboracion(Colaborador colaborador) {
    boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_JURIDICA);
    if (puedoProcesar) {

      //Scanner recibir datos
      //String nombre
      //TipoRubro rubro
      //Double puntosNecesarios
      //String imagen
      //PersonaJuridica empresa

      //Oferta oferta = new Oferta(nombre, rubro, puntosNecesarios, imagen, empresa);

      //Ver donde guardar ofertas
    }

  }

  @Override
  public Double calcularPuntos() {
    return 0.0;
  }

}
