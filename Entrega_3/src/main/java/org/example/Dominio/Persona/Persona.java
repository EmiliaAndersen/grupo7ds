package org.example.Dominio.Persona;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.Rol.Rol;
import org.example.Validador.Usuario;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="persona")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Usa una sola tabla para la herencia
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)  // Columna discriminadora

@Setter
@Getter
public abstract class Persona {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "persona", cascade = CascadeType.PERSIST)
  private List<MedioDeContacto> mediosDeContacto;

  @Setter
  private String direccion;

  @OneToOne
  @JoinColumn(name = "usuario_id",referencedColumnName = "id",nullable = true)
  private Usuario usuario;
//  private Usuario id;


    public void asignarRol(Persona persona, Rol rol){
    rol.persona= persona;
    }
}