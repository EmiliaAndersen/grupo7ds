import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class algortimo_v_c {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Sistema.");

        System.out.println("¿Qué deseas hacer?");
        System.out.println("1. Iniciar Sesion");
        System.out.println("2. Registrarme");
        System.out.print("Opción: ");
        String opcion = scanner.nextLine();

        if (opcion.equals("1")) {
            // BASE DE DATOS
        }
        else if (opcion.equals("2")) {
            registrarUsuario(scanner);
        }
        else {
            System.out.println("Opción no válida.");
        }

        // Aquí puedes guardar los datos en una base de datos, archivo, etc.
        // Por simplicidad, no lo haremos en este ejemplo.
    }


    private static void registrarUsuario(Scanner scanner) {
        System.out.print("Por favor, ingrese un nombre de usuario: ");
        String usuario = scanner.nextLine();

        System.out.print("Por favor, ingrese una contraseña: ");
        String contraseña = scanner.nextLine();

        System.out.println("\nUsuario creado con éxito. Los detalles son:");
        System.out.println("Nombre de usuario: " + usuario);
        System.out.println("Contraseña: " + contraseña);

        guardarUsuarioEnArchivo(usuario, contraseña);
    }

    private static void guardarUsuarioEnArchivo(String usuario, String contraseña) {

        String nombreArchivo = "usuarios.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {

            writer.write(usuario + ":" + contraseña);
            writer.newLine(); // Agregar una nueva línea para separar los registros

        } catch (IOException e) {
            System.out.println("Error al guardar el usuario en el archivo: " + e.getMessage());
        }
    }
}
