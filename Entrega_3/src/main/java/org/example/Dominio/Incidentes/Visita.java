package org.example.Dominio.Incidentes;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Tecnico;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="visita")
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @Column
    private LocalDateTime fechaYHora;

    @Column
    private String comentarios;

    @Column
    private String foto;

    @Column
    @Getter
    @Setter
    private Boolean pudoResolver;

    @Setter
    @ManyToOne
    @JoinColumn(name = "incidente_id")
    public Incidente incidente;

    public Visita(Tecnico tecnico, LocalDateTime fechaYHora) {
        this.tecnico = tecnico;
        this.fechaYHora = fechaYHora;
    }
}
