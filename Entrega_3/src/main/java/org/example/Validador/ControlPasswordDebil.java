package org.example.Validador;

import java.io.BufferedReader;
import java.io.FileReader;

public class ControlPasswordDebil implements Condicion {
  private static String commonPasswordsPath;

  public ControlPasswordDebil() {
    commonPasswordsPath = "Entrega_3/src/main/java/org/example/Validador/passwords.txt";
  }

  @Override
  public Boolean validar(String password) {
    return !EsComun(password);
  }

  public static boolean EsComun(String password){
    try{
      BufferedReader buff = new BufferedReader(new FileReader(commonPasswordsPath));
      String s;
      while((s=buff.readLine())!=null){
        if(s.trim().contains(password)){
          return true;
        }
      }
      buff.close();
    }catch(Exception e){e.printStackTrace();}
    return false;
  }
}