package org.example.Dominio.Heladeras;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.Dominio.Viandas.Vianda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Heladera {
    @Getter
    private PuntoEstrategico ubicacion;
    private ArrayList<Vianda> viandas;
    private LocalDate fechaInicioFuncionamiento;
    @Setter
    @Getter
    private float temperaturaMaxima;
    @Setter
    @Getter
    private float temperaturaMinima;
    public EstadoHeladera estado;

    private List <Suscriptor> suscriptores;
    private List<ActividadHeladera> mesesActiva;

    public Heladera(float temperaturaMaxima, float temperaturaMinima, PuntoEstrategico ubicacion){
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.ubicacion = ubicacion;
        this.viandas = new ArrayList<Vianda>();
        this.fechaInicioFuncionamiento = LocalDate.now();
        this.estado = EstadoHeladera.ACTIVA;
        this.mesesActiva = new ArrayList<ActividadHeladera>();
    }

    public boolean validarTemperaturaFuncional(float unaTemperatura){
        if(unaTemperatura > temperaturaMaxima){
            estado = EstadoHeladera.TEMPERATURA_ALTA;
            return false;
        }
        if(unaTemperatura < temperaturaMinima){
            estado = EstadoHeladera.TEMPERATURA_BAJA;
            return false;
        }
        return true;
    }
    
    public void agregarVianda(Vianda unaVianda){
        this.viandas.add(unaVianda);
        for (Suscriptor suscriptor : suscriptores) {
            if(suscriptor.getTipo() == (TipoSuscripcion.FALTANTES)){
                if(suscriptor.getNumeroAviso() >= viandas.size()){
                    suscriptor.notificarSuscriptor();
                }
            }
        }

    }

    public boolean retirarVianda(){
        if(this.viandas.isEmpty()){
            return false;
        }
        Vianda unaVianda = this.viandas.remove(0);
        unaVianda.retirar();

        for (Suscriptor suscriptor : suscriptores) {
            if(suscriptor.getTipo() == (TipoSuscripcion.RESTANTES)){
                if(suscriptor.getNumeroAviso() <= viandas.size()){
                    suscriptor.notificarSuscriptor();
                }
            }
        }
        return true;
    }

}
