package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.MediosContacto.MedioDeContacto;

import java.util.ArrayList;
import java.util.List;

public class Colaborador extends Rol{
    @Getter
    @Setter
    private List<Colaboracion> colaboraciones;
    @Getter
    @Setter
    private double puntos;


public Colaborador() {
    colaboraciones = new ArrayList<>();
}

public void agregarColaboracion(Colaboracion colaboracion){
    colaboraciones.add(colaboracion);
    colaboracion.procesarColaboracion(this);
}

public Double calcularPuntos(){
    return colaboraciones.stream().mapToDouble(Colaboracion::calcularPuntos).sum();
}
}
