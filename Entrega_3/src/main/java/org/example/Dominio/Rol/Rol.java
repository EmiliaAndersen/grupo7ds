package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Persona.Persona;

import javax.persistence.*;

@MappedSuperclass
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "persona_id",referencedColumnName = "id")
    @Getter
    @Setter
    public Persona persona;


}

