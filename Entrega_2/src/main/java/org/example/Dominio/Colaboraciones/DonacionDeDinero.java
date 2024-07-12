package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDate;

public class DonacionDeDinero extends Colaboracion{
  @Getter
  @Setter
  private LocalDate fecha;
  @Getter
  @Setter
  private Double monto;
  @Getter
  @Setter
  private LocalDate frecuencia;

  public DonacionDeDinero(LocalDate fecha, Double monto, LocalDate frecuencia) {
    this.fecha = fecha;
    this.monto = monto;
    this.frecuencia = frecuencia;
  }

  @Override
  public void procesarColaboracion(Colaborador colaborador) {
    boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_HUMANA) || isTypeOf(colaborador, TipoColaborador.P_JURIDICA);
    if (puedoProcesar) {

      //Leer SCANNER
      //LocalDate fecha;
      //Double monto ;
      //LocalDate frecuencia;

      //DonacionDeDinero donacion = new DonacionDeDinero(fecha, monto, frecuencia);
      //Ver que hace con donacion
    }
  }
  public Double calcularPuntos(){return monto*0.5;}
}
