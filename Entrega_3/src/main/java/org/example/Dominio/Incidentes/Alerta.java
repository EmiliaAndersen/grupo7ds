package org.example.Dominio.Incidentes;


import lombok.Getter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Reportes.GeneradorReporteFallas;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("alerta")
public class Alerta extends Incidente{
    @Enumerated(EnumType.STRING)
    @Getter
    private TipoAlerta tipo;

    public Alerta(TipoAlerta tipo, Heladera heladera, LocalDateTime fechaYHora) {
        this.tipo = tipo;
        this.setHeladera(heladera);
        this.setFechaYHora(fechaYHora);
    }


    public Alerta() {

    }
}
