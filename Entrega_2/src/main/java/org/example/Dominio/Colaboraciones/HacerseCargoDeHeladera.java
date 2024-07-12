package org.example.Dominio.Colaboraciones;

import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import org.example.Dominio.Rol.Colaborador;

public class HacerseCargoDeHeladera extends Colaboracion {
  @Setter
  private LocalDate fechaInicio;

  @Override
  public void procesarColaboracion(Colaborador colaborador) {
    boolean puedoProcesar = isTypeOf(colaborador, TipoColaborador.P_JURIDICA);
    if (puedoProcesar) {
      //Ver
    }
  }

  @Override
  public Double calcularPuntos() {
    LocalDate fechaActual = LocalDate.now();
    Period periodo = Period.between(fechaInicio,fechaActual);
    return (double) ((periodo.getYears()*12 + periodo.getMonths())*5);
  }

  public String getLocalizacionEstrategica(float latitud, float longitud, float radio){
    return "Conectarse a la api";
  }
}
