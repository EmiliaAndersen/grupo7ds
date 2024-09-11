package org.example.Dominio.Colaboraciones;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Rol.Colaborador;
import org.example.Servicio.LocalizacionEstrategicaAPI;

public class HacerseCargoDeHeladera extends Colaboracion {
  @Setter
  private LocalDate fechaInicio;

  @Getter
  private PuntoEstrategico puntoEstrategico;
  @Getter
  private Double radio;


  private final LocalizacionEstrategicaAPI localizacionEstrategicaAPI;

  public HacerseCargoDeHeladera(LocalizacionEstrategicaAPI localizacionEstrategicaAPI, PuntoEstrategico puntoEstrategico, Double radio) {
    this.localizacionEstrategicaAPI = localizacionEstrategicaAPI;
    this.puntoEstrategico = puntoEstrategico;
    this.radio = radio;
  }


  @Override
  public void procesarColaboracion(Colaborador colaborador) {
//
//    if (isTypeOf(colaborador, TipoColaborador.P_JURIDICA)) {
//      //Ver
//    }
  }

  @Override
  public Double calcularPuntos() {
    LocalDate fechaActual = LocalDate.now();
    Period periodo = Period.between(fechaInicio,fechaActual);
    return (double) ((periodo.getYears()*12 + periodo.getMonths())*5);
  }

  public List<PuntoEstrategico> getLocalizacionEstrategica(){

    return localizacionEstrategicaAPI.getPuntoEstrategico(puntoEstrategico, radio);
  }
}
