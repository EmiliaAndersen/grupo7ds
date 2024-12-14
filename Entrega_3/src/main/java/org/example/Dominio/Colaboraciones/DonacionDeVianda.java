package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Viandas.EstadoVianda;
import lombok.Setter;
import org.example.Dominio.Viandas.Vianda;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "donacion_de_vianda")
public class DonacionDeVianda extends Colaboracion{
  
    @OneToOne
    @JoinColumn(name = "vianda_id",referencedColumnName = "id")
    @Setter
    @Getter
    public Vianda vianda;

    @Setter
    @Getter
    public Double cantidad;
  
      public DonacionDeVianda(Vianda vianda, Double cantidad) {
          this.vianda = vianda;
          this.cantidad = cantidad;

      } 

    public DonacionDeVianda() {
    }


  @Override
  public void procesarColaboracion(Colaborador colaborador) {
        
  }
  public Double calcularPuntos(){return 1.5 * cantidad;}
}
