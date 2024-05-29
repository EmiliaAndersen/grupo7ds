package domain.services.pokeapi.services;

import domain.services.pokeapi.entidades.Movimiento;
import domain.services.pokeapi.entidades.Pokemon;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioPokeapi {
    private static ServicioPokeapi instancia = null;
    private final Retrofit retrofit;
    private PokeService pokeService;

    private ServicioPokeapi(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioPokeapi getInstancia() {
        if( instancia == null){
            instancia = new ServicioPokeapi();
        }
        return instancia;
    }

    public Pokemon pokemon(String nombre) throws IOException {
        pokeService = this.retrofit.create(PokeService.class);
        Call<Pokemon> requestPokemon = pokeService.pokemon(nombre);
        Response<Pokemon> pokemonResponse = requestPokemon.execute();
        return pokemonResponse.body();
    }

    public Movimiento movimiento(String nombre) throws IOException {
        pokeService = this.retrofit.create(PokeService.class);
        Call<Movimiento> requestMovimiento = pokeService.movimiento(nombre);
        Response<Movimiento> movimientoResponse = requestMovimiento.execute();
        return movimientoResponse.body();
    }
}
