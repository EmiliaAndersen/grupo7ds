package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Rol.Colaborador;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
@Entity
@Table(name = "donacion_de_dinero")
public class DonacionDeDinero extends Colaboracion{
  @Column
  @Getter
  @Setter
  private LocalDate fecha;
  @Column
  @Getter
  @Setter
  private Double monto;
  @Column
  @Getter
  @Setter
  private LocalDate frecuencia;
  @Transient
  @Getter
  @Setter
  private String proxDon;

  public DonacionDeDinero(LocalDate fecha, Double monto, LocalDate frecuencia) {
    this.fecha = fecha;
    this.monto = monto;
    this.frecuencia = frecuencia;
  }

  public DonacionDeDinero() {

  }

  @Override
  public void procesarColaboracion(Colaborador colaborador) {
    //boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_HUMANA) || isTypeOf(colaborador, TipoColaborador.P_JURIDICA);
    //if (puedoProcesar) {

      //Leer SCANNER
      //LocalDate fecha;
      //Double monto ;
      //LocalDate frecuencia;

      //DonacionDeDinero donacion = new DonacionDeDinero(fecha, monto, frecuencia);
      //Ver que hace con donacion
    //}
  }
  public Double calcularPuntos(){return monto*0.5;}
}
