package org.example.Dominio.Tarjetas;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(name = "uso_tarjeta")
public class UsoTarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Getter
    private LocalDate fecha;
    @Column
    private LocalTime hora;

    @OneToOne
    @JoinColumn(name = "heladera_id",referencedColumnName = "id")
    private Heladera heladera;

    @ManyToOne
    @JoinColumn(name = "tarjeta_vulnerable_id")
    @Setter
    private TarjetaVulnerable tarjetaVulnerable;

    public UsoTarjeta(LocalDate fecha, LocalTime hora, Heladera unaHeladera) {
        this.fecha = fecha;
        this.hora = hora;
        this.heladera = unaHeladera;
    }

    public UsoTarjeta() {

    }
}
