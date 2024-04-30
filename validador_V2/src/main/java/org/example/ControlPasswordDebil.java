package org.example;

import java.io.BufferedReader;
import java.io.FileReader;

public class ControlPasswordDebil implements Condicion {
  private static String commonPasswordsPath;

  public ControlPasswordDebil() {
    //TODO: cambiar path a tu passwords.txt
    commonPasswordsPath = "C:\\Users\\judzi\\OneDrive\\Documentos\\2024\\DDS\\TP\\grupo7ds\\validador_V2\\src\\main\\java\\org\\example\\passwords.txt";
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