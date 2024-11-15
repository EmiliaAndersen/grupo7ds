package org.example;

import org.example.Dominio.MediosContacto.MedioDeContacto;
import org.example.Dominio.Persona.Persona;
import org.example.Dominio.Persona.PersonaHumana;
import org.example.Dominio.Rol.Colaborador;
import org.example.Validador.ControlPasswordDebil;
import org.example.Validador.Nist800;
import org.example.Validador.Usuario;
import org.example.Validador.Validador;
import org.example.Migrador.Migrador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    // --- EJEMPLO MIGRACION ---
    Migrador reg = new Migrador();
    List<PersonaHumana> personas = new ArrayList<>();
    List<Colaborador> colaboradores = new ArrayList<>();
    //reg.Migrar("Entrega_3/src/main/java/org/example/archivo.csv",personas, colaboradores);

    //chequeo q haya salido todo bien
    for (Colaborador colaborador : colaboradores){
      Persona persona = colaborador.getPersona();
      MedioDeContacto contacto = persona.getMediosDeContacto().get(0);
      System.out.println(contacto.getDetalle()) ;
      System.out.println(colaborador.getColaboraciones());
    }

    // --- EJEMPLO VALIDADOR ---
    Scanner scanner = new Scanner(System.in);
    Usuario usuario = new Usuario("dam","123");
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
