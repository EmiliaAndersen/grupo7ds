package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Tarjetas.Tarjeta;

import java.time.LocalDate;

public class PersonaVulnerable extends Rol{
        private LocalDate fechaDeRegristro;
        @Getter
        @Setter
        private int menoresACargo;
        @Setter
        private Tarjeta tarjeta;
}


