package org.example.Dominio.PersonaVulnerable;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Documentos.Documento;
import org.example.Dominio.Heladeras.Heladera;
import org.example.Dominio.Tarjetas.Tarjeta;

import java.time.LocalDate;

public class PersonaVulnerable {

    private String nombre;
    private LocalDate fechaDeNacimiento;
    private LocalDate fechaDeRegristro;
    private String domicilio;
    @Getter
    @Setter
    private int menoresACargo;
    private Documento documento;

}
