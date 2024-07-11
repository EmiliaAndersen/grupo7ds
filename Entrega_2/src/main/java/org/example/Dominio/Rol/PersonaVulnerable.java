package org.example.Dominio.Rol;

import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Tarjetas.Tarjeta;

import java.time.LocalDate;

public class PersonaVulnerable extends Rol{
        private LocalDate fechaDeRegristro;
        // Esto podria implementarse asi
        // private List<PersonaVulnerable> menoresACargo;
        private int menoresACargo;
        private Tarjeta tarjeta;

        public void usarTarjeta(Heladera unaHeladera){
            tarjeta.usar(unaHeladera, menoresACargo);
        }
}


