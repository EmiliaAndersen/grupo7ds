package org.example.Dominio.Tarjetas;

import lombok.Getter;
import org.example.Dominio.Heladeras.Heladera;

import java.time.LocalDate;
import java.time.LocalTime;

public class UsoTarjeta {

    @Getter
    private LocalDate fecha;
    private LocalTime hora;
    private Heladera heladera;

    public UsoTarjeta(LocalDate fecha, LocalTime hora, Heladera unaHeladera) {
        this.fecha = fecha;
        this.hora = hora;
        this.heladera = unaHeladera;
    }
}
