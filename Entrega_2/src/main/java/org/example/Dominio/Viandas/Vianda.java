package org.example.Dominio.Viandas;
import lombok.Getter;

import lombok.Setter;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Heladeras.Heladera;

import java.time.LocalDate;

public class Vianda {
    @Getter
    @Setter
    private String descripcionComida;
    @Getter
    @Setter
    private LocalDate fechaCaducidad;
    @Getter
    @Setter
    private LocalDate fechaDonacion;
    @Getter
    @Setter
    private Heladera heladera;
    @Getter
    @Setter
    private float calorias;
    @Getter
    @Setter
    private float peso;
    @Getter
    @Setter
    private EstadoVianda estadoVianda;

    public Vianda (String descripcionComida,LocalDate fechaCaducidad, LocalDate fechaDonacion, Heladera heladera, float calorias, float peso, EstadoVianda estadoVianda) {

        this.descripcionComida = descripcionComida;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaDonacion = fechaDonacion;
        this.heladera = heladera;
        this.calorias = calorias;
        this.peso = peso;
        this.estadoVianda = estadoVianda;
    }


    public void agregarAHeladera(Heladera heladera){
        this.heladera = heladera;
        this.heladera.agregarVianda(this);
    }

    public void retirar(){
        this.estadoVianda = EstadoVianda.RETIRADA;
    }
}
