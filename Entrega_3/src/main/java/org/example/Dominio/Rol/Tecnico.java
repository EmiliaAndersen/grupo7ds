package org.example.Dominio.Rol;

import lombok.Getter;
import org.example.Dominio.AreaDeCobertura.AreaCobertura;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Incidentes.Visita;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Tecnico extends Rol{
    @Getter
    @Transient
    //TODO: Revisar porque lista
    private List<AreaCobertura> areaCobertura;


    public void registrarVisita(Incidente incidente){
        Visita visita = new Visita(this, LocalDateTime.now());
        incidente.getVisitas().add(visita);
    }
}