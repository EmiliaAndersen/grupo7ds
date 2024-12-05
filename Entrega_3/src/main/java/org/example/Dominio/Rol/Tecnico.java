package org.example.Dominio.Rol;

import lombok.Getter;
import org.example.Dominio.AreaDeCobertura.AreaCobertura;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Incidentes.Visita;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="tecnico")
public class Tecnico extends Rol{

    //TODO: Revisar porque lista
    @Getter
    @Transient
    private List<AreaCobertura> areaCobertura;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL)
    public List<Visita> visitas;


    public void registrarVisita(Incidente incidente){
        Visita visita = new Visita(this, LocalDateTime.now());
        incidente.getVisitas().add(visita);
    }
}