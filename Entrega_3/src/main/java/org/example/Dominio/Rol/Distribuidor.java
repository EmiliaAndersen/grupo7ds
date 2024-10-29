package org.example.Dominio.Rol;

import org.example.Dominio.Tarjetas.TarjetaDistribuidor;

import javax.persistence.*;
import java.util.List;
@Entity
@Table
//TODO: VERIFICAR EL DER SI ES UNA SOLA TABLA O DEBERIAN SER DOS
public class Distribuidor extends Colaborador{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToMany(mappedBy = "distribuidor", cascade = CascadeType.ALL)
  public List<AperturaHeladera> aperturas;

  @OneToOne
  @JoinColumn(name = "tarjeta_distribuidor_id")
  private TarjetaDistribuidor tarjetaDistribuidor;

  public void registrarApertura(AperturaHeladera apertura){
    aperturas.add(apertura);
  }

}
