package org.example.Dominio.Incidentes;


import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Reportes.GeneradorReporteFallas;

import java.time.LocalDateTime;

public class Alerta extends Incidente{
    private TipoAlerta tipo;

    public Alerta(TipoAlerta tipo, Heladera heladera, LocalDateTime fechaYHora) {
        this.tipo = tipo;
        this.setHeladera(heladera);
        this.setFechaYHora(fechaYHora);

        this.notificarReporte();
    }


}
