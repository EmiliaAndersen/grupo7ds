package org.example.Validador;

import java.util.stream.Stream;

public class Nist800 implements Condicion {

  private static final int MIN_LENGTH = 8;
  private static final int MAX_LENGTH = 64;

  private Boolean passwordLongitud(String passsword) {
    return (passsword.length() <= MIN_LENGTH || passsword.length() >= MAX_LENGTH) ? false : true;
  }


  private Boolean passwordHasNumber(String password){
    return password.matches(".*\\d.*");
  }

  @Override
  public Boolean validar(String password) {
    return Stream.of(
        passwordLongitud(password),
        passwordHasNumber(password)
    ).allMatch(Boolean::booleanValue);
  }
}
