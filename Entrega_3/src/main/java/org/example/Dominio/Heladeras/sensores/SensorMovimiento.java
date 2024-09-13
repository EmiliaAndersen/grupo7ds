package org.example.Dominio.Heladeras.sensores;

import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.IncidenteFactory;
import org.example.Dominio.Incidentes.TipoAlerta;

import java.time.LocalDateTime;

public class SensorMovimiento extends Sensor {

    public void enviarAlerta() throws Exception {
        //throw new Exception("Movimiento detectado en la heladera: " + this.heladera.getUbicacion().getNombre());
        IncidenteFactory incidenteFactory = new IncidenteFactory();
        Alerta alerta = incidenteFactory.crearAlerta(TipoAlerta.FRAUDE, this.heladera);
        alerta.notificar();
    }
}
