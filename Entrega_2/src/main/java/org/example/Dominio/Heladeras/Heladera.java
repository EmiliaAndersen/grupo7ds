package org.example.Dominio.Heladeras;

import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Viandas.Vianda;

import java.time.LocalDate;
import java.util.List;

public class Heladera {
    private PuntoEstrategico ubicacion;
    private List<Vianda> viandas;
    private final LocalDate fechaInicioFuncionamiento = LocalDate.now();
    private float temperaturaMaxima;
    private float temperaturaMinima;

    public EstadoHeladera estado;

    public boolean validarTemperaturaFuncional(float unaTemperatura){
        return unaTemperatura >= temperaturaMinima && unaTemperatura <= temperaturaMaxima;
    }
    
    public void agregarVianda(Vianda unaVianda){
        this.viandas.add(unaVianda);
    }

    public void retirarVianda(Vianda unaVianda){
        this.viandas.remove(unaVianda);
        unaVianda.retirar();
    }

}
