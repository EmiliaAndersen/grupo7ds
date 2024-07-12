package org.example.Dominio.PuntosEstrategicos;

import lombok.Getter;

public class PuntoEstrategico {

    @Getter
    private String nombre;
    private float longitud;
    private float latitud;
    private String direccion;

    public float longitud(){
        return this.longitud;
    }

    public float latitud(){
        return this.latitud;
    }
}
