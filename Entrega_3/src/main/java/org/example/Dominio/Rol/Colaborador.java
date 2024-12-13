package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Colaboraciones.Colaboracion;
import org.example.Dominio.Heladeras.EstadoHeladera;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Incidentes.Alerta;
import org.example.Dominio.Incidentes.FallaTecnica;
import org.example.Dominio.Incidentes.TipoAlerta;
import org.example.Dominio.MediosContacto.MedioDeContacto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="colaborador")
public class Colaborador extends Rol{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @OneToMany(mappedBy = "colaborador",cascade = CascadeType.ALL)
    @Getter
    @Setter
    public List<Colaboracion> colaboraciones;

    @Column
    @Getter
    @Setter
    private double puntos;

    public Colaborador() {
        colaboraciones = new ArrayList<>();
        puntos = 0;
    }

    public void agregarColaboracion(Colaboracion colaboracion){
        colaboraciones.add(colaboracion);
        colaboracion.procesarColaboracion(this);
    }

    public Double calcularPuntos(){
    return colaboraciones.stream().mapToDouble(Colaboracion::calcularPuntos).sum();
}


    public void agregarFalla(Heladera healdera){
        FallaTecnica falla = new FallaTecnica(this,healdera, LocalDateTime.now(),"");
        healdera.setEstado(EstadoHeladera.INACTIVA);
    }
}
