package net.iessochoa.manuelmartinez.practica6.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import net.iessochoa.manuelmartinez.practica6.model.Pokemon;
import net.iessochoa.manuelmartinez.practica6.model.PokemonDAO;
import net.iessochoa.manuelmartinez.practica6.model.PokemonDatabase;

import java.util.List;


public class PokemonRepository {
    //implementamos Singleton
    private static volatile PokemonRepository INSTANCE;
    private PokemonDAO mPokemonDao;

    // lista de pokemon como LiveData, que nos permitirá mantener durante la vida de la actividad los
    //datos y además no permitirá observar los datos cuando haya una actualización
    private LiveData<List<Pokemon>> mAllPokemons;

    //patron singleton
    public static PokemonRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PokemonRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    private PokemonRepository(Application application) {
        //creamos la base de datos
        PokemonDatabase db = PokemonDatabase.getDatabase(application);
        //Recuperamos el DAO necesario para el CRUD de la base de datos
        mPokemonDao = db.pokemonDao();
        //Recuperamos la lista como un LiveData
        mAllPokemons = mPokemonDao.allPokemon();
    }

    /**
     * metodo que nos devuelve los pokemon
     */

    public LiveData<List<Pokemon>> getAllPokemons() {
        return mAllPokemons;
    }

    /**
     * clase AsyncTask especializada para lanzar hilos en android.
     */

    private static class inserAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDAO mAsyncTaskDao;

        public inserAsyncTask(PokemonDAO mPokemonDao) {
            mAsyncTaskDao = mPokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            //lo que va dentro de este método se ejecuta en un hilo
            mAsyncTaskDao.insert(pokemons[0]);
            return null;
        }
    }

    /**
     * método que nos lanzará en segundo plano la inserción
     */

    public void insert(Pokemon pokemon) {
        //lanzamos en segundo plano la insercción
        new inserAsyncTask(mPokemonDao).execute(pokemon);
    }

    /**
     * método y clase para el borrado
     */

    public void delete(Pokemon pokemon) {
        new deleteAsyncTask(mPokemonDao).execute(pokemon);
    }

    private static class deleteAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDAO mAsyncTaskDao;

        public deleteAsyncTask(PokemonDAO mPokemonDao) {
            mAsyncTaskDao = mPokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            mAsyncTaskDao.deleteByPokemon(pokemons[0]);
            return null;
        }
    }


}


