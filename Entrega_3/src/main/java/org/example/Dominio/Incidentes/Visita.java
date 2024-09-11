package org.example.Dominio.Incidentes;

import lombok.Getter;
import org.example.Dominio.Rol.Tecnico;

import java.time.LocalDateTime;

public class Visita {
    private Tecnico tecnico;
    private LocalDateTime fechaYHora;
    private String comentarios;
    private String foto;
    @Getter
    private Boolean pudoResolver;

    public Visita(Tecnico tecnico, LocalDateTime fechaYHora) {
        this.tecnico = tecnico;
        this.fechaYHora = fechaYHora;
    }
}
