package org.example.Dominio.MediosContacto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "medio_de_contacto")
public class MedioDeContacto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column
  @Enumerated(EnumType.STRING)
  private TipoMedioContacto tipo;
  @Column
  private String detalle;
}
