package org.example.entidades.Tarjetas;

import lombok.Getter;
import org.example.entidades.Heladeras.Heladera;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TarjetaUso {

    @Getter
    private LocalDate fecha;
    private LocalTime hora;
    private Heladera heladera;

    public TarjetaUso(LocalDate fecha, LocalTime hora, Heladera unaHeladera) {
        this.fecha = fecha;
        this.hora = hora;
        this.heladera = unaHeladera;
    }
}
