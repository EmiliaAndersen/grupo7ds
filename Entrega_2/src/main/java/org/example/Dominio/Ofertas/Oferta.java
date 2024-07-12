package org.example.Dominio.Ofertas;

import lombok.Getter;
import org.example.Dominio.Persona.PersonaJuridica;

public class Oferta {
  @Getter
  private String nombre;
  @Getter
  private TipoRubro rubro;
  @Getter
  private Double puntosNecesarios;
  @Getter
  private String imagen;
  @Getter
  private PersonaJuridica empresa;


  public Oferta (String nombre, TipoRubro rubro, Double puntosNecesarios, String imagen, PersonaJuridica empresa ){
    this.nombre = nombre;
    this.rubro = rubro;
    this.puntosNecesarios= puntosNecesarios;
    this.imagen = imagen;
    this.empresa = empresa;
  }
}


