package org.example.Dominio.Heladeras;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.TipoAlerta;
import org.example.Dominio.Incidentes.Visita;
import org.example.Dominio.PuntosEstrategicos.PuntoEstrategico;
import org.example.Dominio.Suscripciones.Suscriptor;
import org.example.Dominio.Suscripciones.TipoSuscripcion;
import org.example.Dominio.Reportes.GeneradorDeReportes;
import org.example.Dominio.Reportes.GeneradorReporteViandasRetiradas;

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

    public List <Suscriptor>suscriptores;
    private List<ActividadHeladera> mesesActiva;

    public Heladera(float temperaturaMaxima, float temperaturaMinima, PuntoEstrategico ubicacion){
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.ubicacion = ubicacion;
        this.viandas = new ArrayList<Vianda>();
        this.fechaInicioFuncionamiento = LocalDate.now();
        this.estado = EstadoHeladera.ACTIVA;
        this.mesesActiva = new ArrayList<ActividadHeladera>();
        this.suscriptores = new ArrayList<Suscriptor>();
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
      
        GeneradorReporteViandasRetiradas reportes = GeneradorReporteViandasRetiradas.getInstance();
        reportes.update();
        return true;
    }

    public void agregarAlerta(TipoAlerta tipoAlerta){
        Alerta alerta = new Alerta(tipoAlerta,this, LocalDateTime.now());
        this.setEstado(EstadoHeladera.INACTIVA);
    }

    public void agregarSuscriptor(Suscriptor suscriptor){
        suscriptores.add(suscriptor);
    }
}
