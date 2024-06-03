package org.example.entidades.Heladeras;

import java.time.LocalDate;

public class Heladera {
    private PuntoEstrategico ubicacion;
    private int cantidadDeViandas;
    private final LocalDate fechaInicioFuncionamiento = LocalDate.now();
    private float temperaturaMaxima;
    private float temperaturaMinima;

    public EstadoHeladera estado;

    public boolean validarTemperaturaFuncional(float unaTemperatura){
        return unaTemperatura >= temperaturaMinima && unaTemperatura <= temperaturaMaxima;
    }
    
    public void agregarVianda(){
        cantidadDeViandas++;
        //generar vianda y guardarla
    }

    public void retirarVianda(){
        cantidadDeViandas--;
        //retirar vianda de donde se guardaba
    }

}
