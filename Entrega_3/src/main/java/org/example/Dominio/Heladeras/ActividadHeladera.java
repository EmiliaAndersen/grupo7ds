package org.example.Dominio.Heladeras;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "actividad_heladera")
public class ActividadHeladera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heladera_id")
    public Heladera heladera;

    @Enumerated(EnumType.STRING)
    @Getter
    private Mes mesActivo;

}
