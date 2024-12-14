package org.example.Dominio.Tarjetas;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;

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

  @ManyToOne
  @JoinColumn(name = "heladera_id")
  private Heladera heladera;

  public SolicitudPermisos(LocalDateTime fechaYHora, Heladera heladera, TarjetaDistribuidor tarjeta){
    this.fechaYHora = fechaYHora;
    this.heladera = heladera;
    this.tarjetaDistribuidor = tarjeta;
  }

  public SolicitudPermisos() {

  }
}
