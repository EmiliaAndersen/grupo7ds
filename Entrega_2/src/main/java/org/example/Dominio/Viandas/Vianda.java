package org.example.Dominio.Viandas;

import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Heladeras.Heladera;

import java.time.LocalDate;

public class Vianda {
    private String descripcionComida;
    private LocalDate fechaCaducidad;
    private LocalDate fechaDonacion;
    private Colaborador colaborador;
    private Heladera heladera;
    private float calorias;
    private float peso;
    private EstadoVianda estadoVianda;

    public void agregarAHeladera(Heladera heladera){
        this.heladera = heladera;
        this.heladera.agregarVianda(this);
    }

    public void retirar(){
        this.estadoVianda = EstadoVianda.RETIRADA;
    }
}
