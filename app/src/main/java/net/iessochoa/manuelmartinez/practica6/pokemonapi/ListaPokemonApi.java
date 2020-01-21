package net.iessochoa.manuelmartinez.practica6.pokemonapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListaPokemonApi {
    @SerializedName("next")
    private String uriSiguientes;
    @SerializedName("results")
    private ArrayList<PokemonApi> listaPokemon;

    public String getUriSiguientes() {
        return uriSiguientes;
    }

    public void setUriSiguientes(String uriSiguientes) {
        this.uriSiguientes = uriSiguientes;
    }

    public ArrayList<PokemonApi> getListaPokemon() {
        return listaPokemon;
    }

    public void setListaPokemon(ArrayList<PokemonApi> listaPokemon) {
        this.listaPokemon = listaPokemon;
    }
}
