package net.iessochoa.manuelmartinez.practica6.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import net.iessochoa.manuelmartinez.practica6.model.Pokemon;
import net.iessochoa.manuelmartinez.practica6.repository.PokemonRepository;

import java.util.List;


public class PokemonViewModel extends AndroidViewModel {
    private PokemonRepository mRepository;
    private LiveData<List<Pokemon>> mAllPokemons;

    public PokemonViewModel(@NonNull Application application) {
        super(application);
        mRepository = PokemonRepository.getInstance(application);
        //Recuperamos el LiveData de todos los pokemons
        mAllPokemons = mRepository.getAllPokemons();
    }

    /**
     * métodos que nos permiten obtener la lista, borrar y añadir un pokemon
     */

    public LiveData<List<Pokemon>> getAllPokemons() {
        return mAllPokemons;
    }

    //Inserción y borrado que se reflejará automáticamente gracias al observador creado en la actividad
    public void insert(Pokemon pokemon) {
        mRepository.insert(pokemon);
    }

    public void delete(Pokemon pokemon) {
        mRepository.delete(pokemon);
    }
}
