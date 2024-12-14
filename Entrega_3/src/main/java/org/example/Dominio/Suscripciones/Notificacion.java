package org.example.Dominio.Suscripciones;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="notificacion")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column
    private String mensajeEnviado;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "suscriptor_id")
    public Suscriptor suscriptor;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoSuscripcion tipo;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;

    @Getter
    @Setter
    @Column
    private Boolean estaResuelta;

}
