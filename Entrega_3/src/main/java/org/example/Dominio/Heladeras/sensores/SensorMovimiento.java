package org.example.Dominio.Heladeras.sensores;

import org.example.Dominio.Incidentes.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("sensor_movimiento")
public class SensorMovimiento extends Sensor {

    public void enviarAlerta() throws Exception {
        //throw new Exception("Movimiento detectado en la heladera: " + this.heladera.getUbicacion().getNombre());
        IncidenteFactory incidenteFactory = new IncidenteFactory();
        Incidente alerta = incidenteFactory.crearAlerta(TipoAlerta.FRAUDE, this.heladera);
        alerta.notificar();
    }
}
