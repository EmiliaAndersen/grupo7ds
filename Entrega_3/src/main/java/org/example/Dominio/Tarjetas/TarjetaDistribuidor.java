package org.example.Dominio.Tarjetas;

import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.AperturaHeladera;
import org.example.Dominio.Rol.Distribuidor;
import org.example.Servicio.AdministradorPermisos;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
      SolicitudPermisos solicitud = new SolicitudPermisos(LocalDateTime.now());
      solicitudesPasadas.add(solicitud);
      AdministradorPermisos.getInstance().darAcceso(this);
  }
  public void abrirHeladera(Heladera heladera) {
    if (validarPermiso()) {
      AperturaHeladera aperturaHeladera = new AperturaHeladera();
      aperturaHeladera.setHeladera(heladera);
      aperturaHeladera.setFechaYHora(LocalDateTime.now());
      distribuidor.registrarApertura(aperturaHeladera);
    } else {
      throw new RuntimeException("Tiempo Limite Excedido");
    }
  }

}
