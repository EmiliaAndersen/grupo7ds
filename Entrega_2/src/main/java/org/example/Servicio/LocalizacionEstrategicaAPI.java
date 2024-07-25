package org.example.Servicio;

import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;

import java.util.ArrayList;
import java.util.List;

public class LocalizacionEstrategicaAPI {

  public List<PuntoEstrategico> getPuntoEstrategico(PuntoEstrategico puntoEstrategico, Double radio) {
    List<PuntoEstrategico> puntos = new ArrayList<>();

    PuntoEstrategico puntoEstrategico1 = new PuntoEstrategico(
        puntoEstrategico.getNombre(),
        puntoEstrategico.getLongitud(),
        puntoEstrategico.getLatitud(),
        puntoEstrategico.getDireccion()
    );

    PuntoEstrategico puntoEstrategico2 = new PuntoEstrategico(
        puntoEstrategico.getNombre() + " 2",
        puntoEstrategico.getLongitud(),
        puntoEstrategico.getLatitud() + (radio / 2),
        puntoEstrategico.getDireccion()
    );

    PuntoEstrategico puntoEstrategico3 = new PuntoEstrategico(
        puntoEstrategico.getNombre() + " 3",
        puntoEstrategico.getLongitud() + (radio / 2),
        puntoEstrategico.getLatitud() + (radio / 2),
        puntoEstrategico.getDireccion()
    );

    puntos.add(puntoEstrategico1);
    puntos.add(puntoEstrategico2);
    puntos.add(puntoEstrategico3);

    return puntos;
  }
}
