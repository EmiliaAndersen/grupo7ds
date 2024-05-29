package domain.services.pokeapi.entidades;

import java.util.List;

public class Pokemon {
    public String name;
    public List<Movimiento> abilities;
    public Sprite sprites;

    public void showSprite(){
        sprites.showSprite();
    }

    public void showAbilities(){
        for(Movimiento unMovimiento : abilities){
            unMovimiento.showName();
        }
    }
}

