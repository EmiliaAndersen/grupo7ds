package org.example.Dominio.Heladeras.sensores;

import org.example.Dominio.Heladeras.Heladera;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="sensor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Usa una sola tabla para la herencia
@DiscriminatorColumn(name = "tipo_sensor", discriminatorType = DiscriminatorType.STRING)  // Columna discriminadora
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heladera_id")
    public Heladera heladera;

    @Column
    public LocalDateTime fechaHoraUltimoRegistro;
}

