package domain.services.pokeapi.services;

import domain.services.pokeapi.entidades.Movimiento;
import domain.services.pokeapi.entidades.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeService {
    @GET("pokemon/{nombre}")
    Call<Pokemon> pokemon(@Path("nombre") String nombre);

    @GET("ability/{movimiento}")
    Call<Movimiento> movimiento(@Path("movimiento") String movimiento);
}
