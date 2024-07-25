package org.example.Dominio.Colaboraciones;

import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Colaboraciones.TipoColaborador;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Persona.PersonaJuridica;

public abstract class Colaboracion {

  public abstract void procesarColaboracion(Colaborador colaborador);

  public Double calcularPuntos() {
    return (double) 0;
  }

  public static boolean isTypeOf(Colaborador colaborador, TipoColaborador tipo) {
    Persona persona = colaborador.getPersona();
    switch (tipo) {
      case P_JURIDICA:
        return persona instanceof PersonaJuridica;
      case P_HUMANA:
        return persona instanceof PersonaHumana;
      default:
        return false;
    }
  }

}
