package org.example.Dominio.Tarjetas;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "solicitud_permisos")
public class SolicitudPermisos {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  @Getter
  @Setter
  private LocalDateTime fechaYHora; //fecha y hora actual

  @ManyToOne
  @JoinColumn(name = "tarjeta_distribuidor_id")
  @Setter
  @Getter
  private TarjetaDistribuidor tarjetaDistribuidor;

  public SolicitudPermisos(LocalDateTime fechaYHora){
    this.fechaYHora = fechaYHora;
  }

  public SolicitudPermisos() {

  }
}
