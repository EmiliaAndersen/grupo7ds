package org.example.Dominio.Heladeras.sensores;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@Entity
@DiscriminatorValue("sensor_temperatura")
public class SensorTemperatura extends Sensor {

    @Column
    private float ultimaTemperaturaRegistrada;

    public void monitorear(int intervalo) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                float temperatura = pedirTemperatura();
                setUltimaTemperaturaRegistrada(temperatura);
            }
        }, 0, intervalo);
    }

    public float pedirTemperatura(){
        return this.obtenerTemperaturaDeLaAPI();
    }

    private float obtenerTemperaturaDeLaAPI(){
        return (float) (Math.random() * 100);
    }

    public void setUltimaTemperaturaRegistrada(float ultimaTemperaturaRegistrada) {
        this.ultimaTemperaturaRegistrada = ultimaTemperaturaRegistrada;

        fechaHoraUltimoRegistro = LocalDateTime.now();

        this.notificarActualizacion();
    }

    private void notificarActualizacion(){
        boolean valida = heladera.validarTemperaturaFuncional(ultimaTemperaturaRegistrada);
        if(!valida){
            this.enviarAlerta(heladera);
        }
    }

    private void enviarAlerta(Heladera heladera){
        IncidenteFactory incidenteFactory = new IncidenteFactory();
        Incidente alerta = incidenteFactory.crearAlerta(TipoAlerta.TEMPERATURA, this.heladera);
        alerta.notificar();
    }
}
