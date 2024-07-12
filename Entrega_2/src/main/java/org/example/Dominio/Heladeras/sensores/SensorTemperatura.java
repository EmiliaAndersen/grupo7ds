package org.example.Dominio.Heladeras.sensores;

import java.time.LocalDateTime;

public class SensorTemperatura extends Sensor {
    private float ultimaTemperaturaRegistrada;

    // Este metodo es llamado por la API del sensor cuando registra una temperatura
    public void setUltimaTemperaturaRegistrada(float ultimaTemperaturaRegistrada) {
        this.ultimaTemperaturaRegistrada = ultimaTemperaturaRegistrada;

        // Actualizo la hora de registro
        fechaHoraUltimoRegistro = LocalDateTime.now();

        this.notificarActualizacion();
    }

    public void notificarActualizacion(){
        heladera.validarTemperaturaFuncional(ultimaTemperaturaRegistrada);
    }
}
