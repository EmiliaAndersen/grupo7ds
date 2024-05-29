package domain.services.pokeapi.entidades;

import java.util.List;

public class Movimiento {
    public NombreMovimiento ability;

    public List<HabilidadPokemon> pokemon;

    public void showName(){
        System.out.println(ability.name);
    }

    public void showPokemonNames(){
        for (HabilidadPokemon pokemon : pokemon) {
            pokemon.showPokemonName();
        }
    }
}
