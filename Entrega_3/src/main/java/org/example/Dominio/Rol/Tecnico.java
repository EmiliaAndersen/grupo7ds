package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.AreaDeCobertura.AreaCobertura;
import org.example.Dominio.Incidentes.Incidente;
import org.example.Dominio.Incidentes.Visita;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tecnico")
public class Tecnico extends Rol{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    //TODO: Revisar porque lista
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_cobertura_id")
    private AreaCobertura areaCobertura;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL)
    public List<Visita> visitas;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<Incidente> incidentes;


    public void registrarVisita(Incidente incidente){
        Visita visita = new Visita(this, LocalDateTime.now());
        incidente.getVisitas().add(visita);
    }
}