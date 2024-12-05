package org.example.Dominio.PuntosEstrategicos;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Documentos.Documento;

import javax.persistence.*;

@Entity
@Table(name = "punto_estrategico")
public class PuntoEstrategico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Getter
    @Setter
    private String nombre;

    @Column
    @Setter
    @Getter
    private Double longitud;

    @Column
    @Setter
    @Getter
    private Double latitud;

    @Column
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


   @PrePersist
    private void validarCoordenadas() {
       /*  if (longitud != null && longitud < 0) {
            throw new IllegalArgumentException("La longitud no puede ser negativa.");
        }
        if (latitud != null && latitud < 0) {
            throw new IllegalArgumentException("La latitud no puede ser negativa.");
        }*/
    }


}
