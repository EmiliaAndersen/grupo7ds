package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Validador.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Usa una sola tabla para la herencia
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)  // Columna discriminadora

@Setter
@Getter
public abstract class Persona {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Transient
  private List<MedioDeContacto> mediosDeContacto;
  @Column
  private String direccion;
  @Transient
  private Usuario usuario;
  private Usuario id;
}