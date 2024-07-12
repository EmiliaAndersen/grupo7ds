package org.example.Dominio.Colaboraciones;

import java.time.LocalDate;
import java.time.Period;

public class HacerseCargoDeHeladera extends Colaboracion {

  private LocalDate fechaInicio;

  @Override
  public void procesarColaboracion() {

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
