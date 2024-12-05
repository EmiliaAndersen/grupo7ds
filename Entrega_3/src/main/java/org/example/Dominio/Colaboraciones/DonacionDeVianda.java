package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Viandas.EstadoVianda;
import lombok.Setter;
import org.example.Dominio.Viandas.Vianda;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "donacion_de_vianda")
public class DonacionDeVianda extends Colaboracion{

  @OneToOne
  @JoinColumn(name = "vianda_id",referencedColumnName = "id")
  @Setter
  @Getter
  public Vianda vianda;

    public DonacionDeVianda(Vianda viandas) {
        this.vianda = viandas;
    }

  public DonacionDeVianda() {

  }

  @Override
  public void procesarColaboracion(Colaborador colaborador) {
        
//      boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_HUMANA);
//      if (puedoProcesar) {
//        //SCANNER PEDIR Y GUARDAR
//        //String descripcionComida;
//        //LocalDate fechaCaducidad;
//        //LocalDate fechaDonacion;
//        //Heladera heladera;
//        //Float calorias;
//        //Float peso;
//        //EstadoVianda estadoVianda;
//
//        //Vianda vianda = new Vianda(descripcionComida,fechaCaducidad,fechaDonacion,heladera,calorias,peso,estadoVianda);
//
//       // viandas.add(vianda);
//      }
  }
  public Double calcularPuntos(){return 1.5;}
}
