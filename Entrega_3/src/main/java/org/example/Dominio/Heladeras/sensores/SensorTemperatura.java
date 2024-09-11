package org.example.Dominio.Heladeras.sensores;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.TipoAlerta;

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
        boolean valida = heladera.validarTemperaturaFuncional(ultimaTemperaturaRegistrada);
        if(!valida){
            this.enviarAlerta(heladera);
        }
    }

    private void enviarAlerta(Heladera heladera){
        Alerta alerta = new Alerta(TipoAlerta.TEMPERATURA, heladera, LocalDateTime.now());
        alerta.notificar();
    }
}
