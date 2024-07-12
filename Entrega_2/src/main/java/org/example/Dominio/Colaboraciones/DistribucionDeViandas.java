package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDate;

public class DistribucionDeViandas extends Colaboracion{
  @Getter
  @Setter
  private Heladera heladeraOrigen;
  @Getter
  @Setter
  private Heladera heladeraDestino;
  @Getter
  @Setter
  private String motivo;
  @Getter
  @Setter
  private LocalDate fecha;


  public DistribucionDeViandas(Heladera heladeraOrigen, Heladera heladeraDestino,String motivo, LocalDate fecha ){
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
    this.motivo = motivo;
    this.fecha = fecha;
  }
  @Override
  public void procesarColaboracion(Colaborador colaborador) {
    boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_HUMANA);
    if (puedoProcesar) {

      //Scanner

      //Heladera heladeraOrigen;
      //Heladera heladeraDestino;
      // String motivo;
      //LocalDate fecha

      //DistribucionDeViandas distribucion = new DistribucionDeViandas(heladeraOrigen, heladeraDestino, motivo, fecha);

      //Ver que hace despues
    }
  }
  public Double calcularPuntos(){return 1.0;}
}

