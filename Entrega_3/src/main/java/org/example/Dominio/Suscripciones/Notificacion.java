package org.example.Dominio.Suscripciones;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="notificacion")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String mensajeEnviado;

    @ManyToOne
    @JoinColumn(name = "suscriptor_id")
    public Suscriptor suscriptor;
}
