package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Rol.Colaborador;

public class Colaboracion {
  public void procesarColaboracion() {
  }

  public Double calcularPuntos() {
    return (double) 0;
  }

  public static boolean isTypeOf(Colaborador colaborador, Class<?> type) {
    return type.isInstance(colaborador.getPersona());
  }

}
