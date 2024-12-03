package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Rol.Colaborador;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "ofrecer_productos")
public class OfrecerProductos extends Colaboracion{

  @Column (nullable = false)
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

      @PrePersist
    private void validarMonto() {
      if (this.tipoProducto == null || this.tipoProducto.isEmpty()) {
        throw new IllegalArgumentException("El tipo de producto no puede ser nulo o vacío");
    }
        if (this.monto < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
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
