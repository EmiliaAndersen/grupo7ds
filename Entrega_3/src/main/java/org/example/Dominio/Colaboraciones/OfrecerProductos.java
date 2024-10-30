package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Rol.Colaborador;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ofrecer_productos")
public class OfrecerProductos extends Colaboracion{

  @Column
  public String tipoProducto;
  @Column
  public String marca;
  @Column
  public int monto;

  public OfrecerProductos(String tipoProducto, String marca, int monto) {
    this.tipoProducto = tipoProducto;
    this.marca = marca;
    this.monto = monto;
  }

  public OfrecerProductos() {

  }

  @Override
  public void procesarColaboracion(Colaborador colaborador) {
//    boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_JURIDICA);
//    if (puedoProcesar) {
//
//      //Scanner recibir datos
//      //String nombre
//      //TipoRubro rubro
//      //Double puntosNecesarios
//      //String imagen
//      //PersonaJuridica empresa
//
//      //Oferta oferta = new Oferta(nombre, rubro, puntosNecesarios, imagen, empresa);
//
//      //Ver donde guardar ofertas
//    }

  }

  @Override
  public Double calcularPuntos() {
    return 0.0;
  }

}
