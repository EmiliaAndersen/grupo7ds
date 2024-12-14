package org.example.Servicio;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Tarjetas.TarjetaDistribuidor;

import java.time.LocalDateTime;

public class AdministradorPermisos {

  private static AdministradorPermisos instance;

  @Getter
  @Setter
  private long tiempoLimite = 3;


  private AdministradorPermisos() {}

  public static  AdministradorPermisos getInstance() {
    if (instance == null) {
      instance = new AdministradorPermisos();
    }
    return instance;
  }

  public boolean calcularTiempoPermitidoDeAcceso(){
  return true; //TODO: Creo que esta de mas con el de abajo alcanzaria
  }

  public void darAcceso(TarjetaDistribuidor tarjeta){
    tarjeta.setPermisoHasta(LocalDateTime.now().plusHours(tiempoLimite));
  }
}
