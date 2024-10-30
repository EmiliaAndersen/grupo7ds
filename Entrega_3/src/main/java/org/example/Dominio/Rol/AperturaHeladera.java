package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Tarjetas.TarjetaDistribuidor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="apertura_heladera")
public class AperturaHeladera {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;


  @OneToOne
  @JoinColumn(name = "heladera_id",referencedColumnName = "id")
  @Getter
  @Setter
  private Heladera heladera;

  @Column
  @Getter
  @Setter
  private LocalDateTime fechaYHora;

  @ManyToOne
  @JoinColumn(name = "distribuidor_id")
  @Setter
  @Getter
  private Distribuidor distribuidor;
}
