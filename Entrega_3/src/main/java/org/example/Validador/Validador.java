package org.example.Validador;

import java.util.ArrayList;
import java.util.List;

public class Validador {
  private List<Condicion> filtros = new ArrayList<>();

  public void addFiltro(Condicion condicion) {
    filtros.add(condicion);
  }
  public Boolean validarPassword(String password) {
    return this.filtros.stream().allMatch(filtro -> filtro.validar(password));
  }
}
