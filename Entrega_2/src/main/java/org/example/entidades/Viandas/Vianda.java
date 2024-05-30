package org.example.entidades.Viandas;

import org.example.entidades.Colaborador;
import org.example.entidades.Heladeras.Heladera;

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
}
