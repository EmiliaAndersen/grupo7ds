package org.example.Dominio.Incidentes;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Rol.Colaborador;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("falla_tecnica")
//@Table(name="falla_tecnica")
public class FallaTecnica extends Incidente {
    public FallaTecnica() {}

    @OneToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id", nullable = false)
    private Colaborador reportero;
    @Column
    private String description;
    @Column
    private String foto;

    public FallaTecnica(Colaborador colaborador, Heladera heladera, LocalDateTime fechaYHora, String description) {
        this.reportero = colaborador;
        this.description = description;
        this.setHeladera(heladera);
        this.setFechaYHora(fechaYHora);
    }
}
