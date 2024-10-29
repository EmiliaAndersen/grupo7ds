package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Tarjetas.TarjetaVulnerable;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "persona_vulnerable")
public class PersonaVulnerable extends Rol{

        @Column
        @Getter
        @Setter
        private LocalDate fechaDeRegristro;
        @Column
        @Getter
        @Setter
        private int menoresACargo;

        @OneToOne
        @JoinColumn(name = "tarjeta_vulnerable_id",referencedColumnName = "id")
        @Setter
        private TarjetaVulnerable tarjetaVulnerable;
}


