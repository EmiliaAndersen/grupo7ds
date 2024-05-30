package org.example.entidades.Tarjetas;

import org.example.entidades.Heladeras.Heladera;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Tarjeta {
    private String codigo;
    private List<TarjetaUso> usos;

    public void usar(Heladera unaHeladera, int menoresACargo) {
        if (this.validarUso(menoresACargo)) {
            TarjetaUso nuevoUso = new TarjetaUso(LocalDate.now(), LocalTime.now(), unaHeladera);
            usos.add(nuevoUso);
        } else {
            System.out.println("Limite de usos diarios alcanzado para la tarjeta: " + codigo);
        }
    }

    private boolean validarUso(int menoresACargo){
        List<TarjetaUso> usosHoy = this.usosDe(LocalDate.now());
        return usosHoy.size() < 4 + 2*menoresACargo;
    }

    private List<TarjetaUso> usosDe(LocalDate unaFecha){
        return usos.stream().filter(c -> c.getFecha().isEqual(unaFecha)).toList();
    }
}
