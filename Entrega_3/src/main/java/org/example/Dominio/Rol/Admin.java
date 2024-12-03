package org.example.Dominio.Rol;

import lombok.Getter;
import lombok.Setter;
import org.example.Dominio.Rol.Rol;

import javax.persistence.*;

@Entity
@Table(name="admin")
public class Admin extends Rol {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public Admin() {

  }

}
