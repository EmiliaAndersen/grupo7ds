package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Colaborador;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "distribucion_de_viandas")
public class DistribucionDeViandas extends Colaboracion{


  @OneToOne
  @JoinColumn(name = "heladera_origen_id",referencedColumnName = "id")
  @Getter
  @Setter
  private Heladera heladeraOrigen;

  @OneToOne
  @JoinColumn(name = "heladera_destino_id",referencedColumnName = "id")
  @Getter
  @Setter
  private Heladera heladeraDestino;


  @Column
  @Getter
  @Setter
  private Double cantidad;


  @Column
  @Getter
  @Setter
  private String motivo;
  
  @Column
  @Getter
  @Setter
  private LocalDate fecha;


  public DistribucionDeViandas(Heladera heladeraOrigen, Heladera heladeraDestino, Double cant , String motivo, LocalDate fecha){
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
    this.motivo = motivo;
    this.fecha = fecha;
    this.cantidad= cant;
  }

  public DistribucionDeViandas() {

  }

  @Override
  public void procesarColaboracion(Colaborador colaborador) {  }

  public Double calcularPuntos(){return  this.cantidad;}
}

