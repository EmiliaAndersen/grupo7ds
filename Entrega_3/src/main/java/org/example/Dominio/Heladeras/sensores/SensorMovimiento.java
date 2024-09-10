package org.example.Dominio.Heladeras.sensores;

import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.TipoAlerta;

import java.time.LocalDateTime;

public class SensorMovimiento extends Sensor {

    public void enviarAlerta() throws Exception {
        //throw new Exception("Movimiento detectado en la heladera: " + this.heladera.getUbicacion().getNombre());
        Alerta alerta = new Alerta(TipoAlerta.FRAUDE, heladera, LocalDateTime.now());
        alerta.notificar();
    }
}
