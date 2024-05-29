package org.example;

import org.example.Validador.ControlPasswordDebil;
import org.example.Validador.Nist800;
import org.example.Validador.Usuario;
import org.example.Validador.Validador;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Usuario usuario = new Usuario();
    Validador validador = new Validador();
    Nist800 nist800 = new Nist800();
    ControlPasswordDebil controlPasswordDebil = new ControlPasswordDebil();
    validador.addFiltro(controlPasswordDebil);
    validador.addFiltro(nist800);


    System.out.println("Bienvenido al Sistema.");

    System.out.println("¿Qué deseas hacer?");
    System.out.println("1. Iniciar Sesion");
    System.out.println("2. Registrarme");
    System.out.print("Opción: ");
    String opcion = scanner.nextLine();

    if (opcion.equals("1")) {
      System.out.println("En desarollo...");
    }
    else if (opcion.equals("2")) {
      System.out.println("Ingrese Usuario");
      String usuarioUsuario = scanner.nextLine();
      usuario.setUsuario(usuarioUsuario);

      System.out.println("Ingrese Contrasenia");
      String contrsasenia = scanner.nextLine();
        if (validador.validarPassword(contrsasenia)){
          usuario.setContrasenia(contrsasenia);
          System.out.println("Usuario Registrado. \nEn desarollo...");
        } else {
          System.out.println("Contraseña Debil.");
        }

    }
    else {
      System.out.println("Opción no válida.");
    }
  }

}
