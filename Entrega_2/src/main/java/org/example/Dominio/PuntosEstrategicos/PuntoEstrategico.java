package org.example.Dominio.PuntosEstrategicos;

import lombok.Getter;
import lombok.Setter;

public class PuntoEstrategico {

    @Getter
    @Setter
    private String nombre;
    @Setter
    @Getter
    private Double longitud;
    @Setter
    @Getter
    private Double latitud;
    @Setter
    @Getter
    private String direccion;

    // Constructor
    public PuntoEstrategico(String nombre, Double longitud, Double latitud, String direccion) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
        this.direccion = direccion;
    }

    public PuntoEstrategico() {

    }

    public Double longitud(){
        return this.longitud;
    }

    public Double latitud(){
        return this.latitud;
    }


}
