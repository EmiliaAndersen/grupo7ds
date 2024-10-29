package org.example.Dominio.Tarjetas;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Random;

@MappedSuperclass
public class Tarjeta {

  @Getter
  @Setter
  private String codigo;

  protected String generarCodigo(){
    String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijoklmnopqrstuvwxyz1234567890";
    Random random = new Random();
    StringBuilder codigo = new StringBuilder();

    for (int i = 0; i < 20; i++) {
      int index = random.nextInt(caracteres.length());
      codigo.append(caracteres.charAt(index));
    }
    return codigo.toString();
  }
}
