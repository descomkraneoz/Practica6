package net.iessochoa.manuelmartinez.practica6.pokemonapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServicePokeApi {

    //con esto se le pasan los valores y nos devuelve estos ya formateados y en java
    @GET("pokemon")
    Call<ListaPokemonApi> getListaPokemon(@Query("limit") int limit,
                                          @Query("offset") int offset);

}
