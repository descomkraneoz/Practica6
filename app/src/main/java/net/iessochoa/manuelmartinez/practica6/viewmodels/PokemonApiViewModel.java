package net.iessochoa.manuelmartinez.practica6.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import net.iessochoa.manuelmartinez.practica6.model.Pokemon;
import net.iessochoa.manuelmartinez.practica6.repository.PokemonRepository;

import java.util.List;

public class PokemonApiViewModel extends AndroidViewModel {
    //repositorio
    private PokemonRepository mRepository;
    //LiveData de los pokemon recuperado del servicio web hasta el momento
    private LiveData<List<Pokemon>> mAllPokemons;

    public PokemonApiViewModel(@NonNull Application application) {
        super(application);
        mRepository = PokemonRepository.getInstance(application);
        //asignamos el LiveData de los pokemon para observarlo en la actividad
        //cuando cambie y mostrar el recyclerView
        mAllPokemons = mRepository.getListaPokemonApiLiveData();
    }

    public void getListaSiguientePokemonApi() {
        //si ya tenemos 40 pokemon, traemos los a partir del 40
        List<Pokemon> listaPokemon = mAllPokemons.getValue();
        Pokemon ultimoPokemon = listaPokemon.get(listaPokemon.size() - 1);
        int pokemonIndiceDesde = ultimoPokemon.getId();
        //Buscamos y añadimos los siguientes 20 pokemon a la lista.
        // Actualizará el LiveDate y responderá el observador en la activity
        mRepository.getListaSiguientePokemonApi(pokemonIndiceDesde);
    }

    public LiveData<List<Pokemon>> getListaPokemonApi() {
        return mAllPokemons;
    }


}
