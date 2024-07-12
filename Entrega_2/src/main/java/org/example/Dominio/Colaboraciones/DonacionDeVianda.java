package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Viandas.EstadoVianda;
import org.example.Dominio.Viandas.Vianda;

import java.time.LocalDate;
import java.util.List;

public class DonacionDeVianda extends Colaboracion{
  private List<Vianda> viandas; //TODO: Agregar las viandas

  @Override
  public void procesarColaboracion(Colaborador colaborador) {
      boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_HUMANA);
      if (puedoProcesar) {
        //SCANNER PEDIR Y GUARDAR
        //String descripcionComida;
        //LocalDate fechaCaducidad;
        //LocalDate fechaDonacion;
        //Heladera heladera;
        //Float calorias;
        //Float peso;
        //EstadoVianda estadoVianda;

        //Vianda vianda = new Vianda(descripcionComida,fechaCaducidad,fechaDonacion,heladera,calorias,peso,estadoVianda);

       // viandas.add(vianda);
      }
  }
  public Double calcularPuntos(){return viandas.size() *1.5;}
}
