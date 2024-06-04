package org.example.Dominio.Ofertas;

import lombok.Getter;
import org.example.Dominio.Colaboradores.PersonaJuridica;

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



}
