package org.example.Dominio.Incidentes;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Colaborador;

import java.time.LocalDateTime;

public class FallaTecnica extends Incidente {
    private Colaborador reportero;
    private String description;
    private String foto;

    public FallaTecnica(Colaborador colaborador, Heladera heladera, LocalDateTime fechaYHora) {
        this.reportero = colaborador;
        this.setHeladera(heladera);
        this.setFechaYHora(fechaYHora);
    }
}
