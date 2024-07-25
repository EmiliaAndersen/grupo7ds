package org.example.Dominio.Heladeras.sensores;

public class SensorMovimiento extends Sensor {

    public void enviarAlerta() throws Exception {
        System.out.println("Enviando Alerta");
        throw new Exception("Movimiento detectado en la heladera: " + this.heladera.getUbicacion().getNombre());
    }
}
