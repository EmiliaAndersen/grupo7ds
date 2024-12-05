package org.example.Dominio.Ofertas;

import lombok.Getter;
import org.example.Dominio.Persona.PersonaJuridica;

import javax.persistence.*;

@Entity
@Table(name="oferta")
public class Oferta {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  @Getter
  private String nombre;
  @Column
  @Getter
  @Enumerated(EnumType.STRING)
  private TipoRubro rubro;
  @Column
  @Getter
  private Double puntosNecesarios;
  @Column
  @Getter
  private String imagen;
  @Transient
  @Getter
  private PersonaJuridica empresa;


  public Oferta (String nombre, TipoRubro rubro, Double puntosNecesarios, String imagen, PersonaJuridica empresa ){
    this.nombre = nombre;
    this.rubro = rubro;
    this.puntosNecesarios= puntosNecesarios;
    this.imagen = imagen;
    this.empresa = empresa;
  }

  public Oferta() {

  }
}


