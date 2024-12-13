package org.example.Dominio.Viandas;
import lombok.Getter;

import lombok.Setter;
import org.example.Dominio.Rol.Colaborador;
import org.example.Dominio.Heladeras.Heladera;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="vianda")
public class Vianda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Getter
    @Setter
    private String descripcionComida;

    @Column
    @Getter
    @Setter
    private LocalDate fechaCaducidad;

    @Column
    @Getter
    @Setter
    private LocalDate fechaDonacion;

    @ManyToOne
    @JoinColumn(name = "heladera_id")
    @Getter
    @Setter
    private Heladera heladera;


    @Column
    @Getter
    @Setter
    private float calorias;

    @Column
    @Getter
    @Setter
    private float peso;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private EstadoVianda estadoVianda;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    @Getter
    @Setter
    private Colaborador colaborador;

    public Vianda (String descripcionComida,LocalDate fechaCaducidad, LocalDate fechaDonacion, Heladera heladera, float calorias, float peso, EstadoVianda estadoVianda, Colaborador colaborador) {

        this.descripcionComida = descripcionComida;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaDonacion = fechaDonacion;
        this.heladera = heladera;
        this.calorias = calorias;
        this.peso = peso;
        this.estadoVianda = estadoVianda;
        this.colaborador = colaborador;
    }

        @PrePersist
        private void validarFechas() {
            if (fechaDonacion != null && fechaCaducidad != null && fechaDonacion.isAfter(fechaCaducidad)) {
                throw new IllegalArgumentException("La fecha de donación no puede ser posterior a la fecha de caducidad.");
            }

}

    public Vianda() {

    }


    public void agregarAHeladera(Heladera heladera){
        this.heladera = heladera;
        this.heladera.agregarVianda(this);
    }

    public void retirar(){
        this.estadoVianda = EstadoVianda.RETIRADA;
        this.heladera = null;
    }
}
