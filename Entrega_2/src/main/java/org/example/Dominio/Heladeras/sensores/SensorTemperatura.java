package org.example.Dominio.Heladeras.sensores;

import java.time.LocalDateTime;

public class SensorTemperatura extends Sensor {
    private float ultimaTemperaturaRegistrada;

    public void setUltimaTemperaturaRegistrada(float ultimaTemperaturaRegistrada) {
        // Este dato deberia recibirlo de alguna forma del sensor fisico.
        this.ultimaTemperaturaRegistrada = ultimaTemperaturaRegistrada;

        // Actualizo la hora de registro
        fechaHoraUltimoRegistro = LocalDateTime.now();
    }

    public void notificarActualizacion(){
        heladera.validarTemperaturaFuncional(ultimaTemperaturaRegistrada);
    }
}
