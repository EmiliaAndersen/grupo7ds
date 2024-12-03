package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;

import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.Tarjetas.TarjetaVulnerable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Entity
@Table(name = "persona_vulnerable")
public class PersonaVulnerable extends Rol{

        @Column
        @Getter
        @Setter
        public String nombre;

        @Column
        @Getter
        @Setter
        public LocalDate fechaDeNac;

        @Column
        @Getter
        @Setter
        public TipoSituacion tipo ;
        
        @Setter
        @OneToOne
        @JoinColumn(name = "documento_id", referencedColumnName = "id", unique = true, nullable = true)
        public Documento Documento;

        @Column
        @Getter
        @Setter
        private LocalDate fechaDeRegristro;
        
        @Column
        @Getter
        @Setter
        public Integer menoresACargo;

        @OneToOne
        @JoinColumn(name = "tarjeta_vulnerable_id",referencedColumnName = "id")
        @Setter
        private TarjetaVulnerable tarjetaVulnerable;

        @PrePersist
        private void validarMenoresACargo() {
            if (menoresACargo != null && menoresACargo > 15) {
                throw new IllegalArgumentException("No se puede tener más de 15 menores a cargo.");
            }
        }

}
