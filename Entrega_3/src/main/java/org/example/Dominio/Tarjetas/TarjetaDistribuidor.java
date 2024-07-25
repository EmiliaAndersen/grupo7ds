package org.example.Dominio.Tarjetas;

import lombok.Setter;
import org.example.Dominio.Rol.AperturaHeladera;
import org.example.Dominio.Rol.Distribuidor;

import java.time.LocalDateTime;
import java.util.List;

public class TarjetaDistribuidor extends Tarjeta {
    private Distribuidor distribuidor;

    @Setter
    private LocalDateTime permisoHasta;

    private List<SolicitudPermisos> solicitudesPasadas;

    public boolean validarPermiso(){
      return permisoHasta.isAfter(LocalDateTime.now());
    }

  public void solicitarAcceso(){
      //TODO: que haria este metodo?

  }
  public void abrirHeladera(){
    AperturaHeladera aperturaHeladera = new AperturaHeladera();
    //TODO: que haria este metodo?
  }

}
