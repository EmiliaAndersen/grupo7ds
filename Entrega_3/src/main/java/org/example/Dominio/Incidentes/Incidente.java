package org.example.Dominio.Incidentes;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;

import java.time.LocalDateTime;
import java.util.List;

public class Incidente {
    @Setter
    private Heladera heladera;
    @Setter
    private LocalDateTime fechaYHora;
    @Getter
    private List<Visita> visitas;

    public void reportar(){

    }
    public void notificar(){

    }
}
