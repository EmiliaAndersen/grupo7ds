package org.example.Dominio.Heladeras;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.TipoAlerta;
import org.example.Dominio.Incidentes.Visita;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Viandas.Vianda;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Setter
    public EstadoHeladera estado;
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
            this.agregarAlerta(TipoAlerta.TEMPERATURA);
            return false;
        }
        if(unaTemperatura < temperaturaMinima){
            estado = EstadoHeladera.TEMPERATURA_BAJA;
            this.agregarAlerta(TipoAlerta.TEMPERATURA);
            return false;
        }
        return true;
    }
    
    public void agregarVianda(Vianda unaVianda){
        this.viandas.add(unaVianda);
    }

    public boolean retirarVianda(){
        if(this.viandas.isEmpty()){
            return false;
        }
        Vianda unaVianda = this.viandas.remove(0);
        unaVianda.retirar();
        return true;
    }

    public void agregarAlerta(TipoAlerta tipoAlerta){
        Alerta alerta = new Alerta(tipoAlerta,this, LocalDateTime.now());
        this.setEstado(EstadoHeladera.INACTIVA);
    }
}
