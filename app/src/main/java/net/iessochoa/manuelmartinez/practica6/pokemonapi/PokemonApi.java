package net.iessochoa.manuelmartinez.practica6.pokemonapi;

import com.google.gson.annotations.SerializedName;

import net.iessochoa.manuelmartinez.practica6.model.Pokemon;

import java.util.Date;

public class PokemonApi {
    //le cambiamos el nombre original del JSON
    @SerializedName("name")
    private String nombrePokemon;
    //le dejamos el nombre original
    private String url;

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Pokemon pokemonApiToPokemon() {
        int id;
        //obtenemos el id del pokemon
        String[] urlPartes = url.split("/");
        id = Integer.parseInt(urlPartes[urlPartes.length - 1]);
        return new Pokemon(id, nombrePokemon, new Date());
    }

}
