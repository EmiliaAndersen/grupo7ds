package domain;

import domain.services.pokeapi.services.ServicioPokeapi;
import domain.services.pokeapi.entidades.Pokemon;
import domain.services.pokeapi.entidades.Movimiento;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        ServicioPokeapi servicioPokeapi = ServicioPokeapi.getInstancia();

        System.out.println("Que tipo de busqueda desea realizar?");
        System.out.println("1. Nombre de Pokemon");
        System.out.println("2. Nombre de movimiento");

        Scanner entradaScanner = new Scanner(System.in);
        int idRequest = Integer.parseInt(entradaScanner.nextLine());

        if (idRequest == 1){
            System.out.println("Ingrese el nombre del pokemon a buscar:");
            System.out.println("(Haga uso de kebab-case)");

            String nombrePokemon = entradaScanner.nextLine();
            Pokemon pokemon = servicioPokeapi.pokemon(nombrePokemon);

            pokemon.showSprite();
            pokemon.showAbilities();
        }
        if(idRequest == 2){
            System.out.println("Ingrese el nombre de la habilidad a buscar:");
            System.out.println("(Haga uso de kebab-case)");

            String nombreHabilidad = entradaScanner.nextLine();
            Movimiento movimiento = servicioPokeapi.movimiento(nombreHabilidad);

            movimiento.showPokemonNames();
        }
    }
}