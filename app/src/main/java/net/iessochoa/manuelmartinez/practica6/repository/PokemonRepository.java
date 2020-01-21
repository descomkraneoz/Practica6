package net.iessochoa.manuelmartinez.practica6.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.iessochoa.manuelmartinez.practica6.model.Pokemon;
import net.iessochoa.manuelmartinez.practica6.model.PokemonDAO;
import net.iessochoa.manuelmartinez.practica6.model.PokemonDatabase;
import net.iessochoa.manuelmartinez.practica6.pokemonapi.ListaPokemonApi;
import net.iessochoa.manuelmartinez.practica6.pokemonapi.PokemonApi;
import net.iessochoa.manuelmartinez.practica6.pokemonapi.WebServicePokeApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PokemonRepository {
    static String TAG = "Pokemon:";
    //implementamos Singleton
    private static volatile PokemonRepository INSTANCE;
    private PokemonDAO mPokemonDao;

    // lista de pokemon como LiveData, que nos permitirá mantener durante la vida de la actividad los
    //datos y además no permitirá observar los datos cuando haya una actualización
    private LiveData<List<Pokemon>> mAllPokemons;

    //**************************************WebApi**********************************************
    //nos vamos a traer los pokemon de 20 en 20
    private final int limit = 20;
    //objeto retrofit que realiza las tareas del servicio web
    private Retrofit retrofit;
    //contiene los métodos del CRUD del servicio. En nuestro caso sólo una consulta
    private WebServicePokeApi servicioWebPokemon;
    //lista de pokemon que va recuperando. Los pokemon los traemos de 20 en 20 y los
    //añadimos al final de esta lista
    private ArrayList<Pokemon> listaPokemonApi;
    //El objeto mutableLiveData, permite mantener el observer en la ui de usuario
    //cuando se modifique la lista y actualizar el recyclerView
    private MutableLiveData<List<Pokemon>> listaPokemonApiLiveData;
    //***********************************FINAL WebApi********************************************

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

    /**
     * Transforma un arrayList de PokemonApi de JSON a objetos Pokemon
     *
     * @param lista
     * @return lista transformada.
     */
    private ArrayList<Pokemon> transformaListaAPokemon(ArrayList<PokemonApi> lista) {
        ArrayList<Pokemon> listaTransformada = new ArrayList<Pokemon>();
        Pokemon pokemon;
        for (PokemonApi pokemonApi : lista) {
            pokemon = pokemonApi.pokemonApiToPokemon();
            listaTransformada.add(pokemon);
            //mostramos los pokemon en el log
            Log.d(TAG, pokemon.getId() + "-" + pokemon.getNombre());
        }
        return listaTransformada;
    }

    /**
     * Este método nos devuelve la lista de pokemon actualizada con los siguientes pokemon desde offset
     *
     * @param offset: desde qué indice de pokemon queremos traer los
     *                pokemon(ejemplo: desde el pokemon 80 del servicio)
     */
    public void getListaSiguientePokemonApi(final int offset) {
        //si no está abierto retrofit, es la primera vez, por lo que inicializamos variables
        if (retrofit == null) {
            iniciaRetrofit();
        }
        //creamos la llamada al servicio con los siguientes 20 pokemon desde el indice offset
        Call<ListaPokemonApi> listaPokemonApiCall = servicioWebPokemon.getListaPokemon(limit, offset);
        //hacemos la llamada
        listaPokemonApiCall.enqueue(new Callback<ListaPokemonApi>() {
            //si recibe correctamente
            @Override
            public void onResponse(Call<ListaPokemonApi> call, Response<ListaPokemonApi> response) {
                if (response.isSuccessful()) {
                    //obtenemos el objeto creado a partir del JSON
                    Log.d(TAG, "Pokemons desde offset: " + offset);
                    ListaPokemonApi pokemonRespuesta = response.body();
                    //Transformamos la lista de pokemon del JSON a nuestros objetos POkemon
                    ArrayList<Pokemon> lista = transformaListaAPokemon(pokemonRespuesta.getListaPokemon());
                    //añadimos al final los nuevos pokemon
                    listaPokemonApi.addAll(lista);
                    //actualizamos el LiveData para que reaccione el observable
                    listaPokemonApiLiveData.setValue(listaPokemonApi);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ListaPokemonApi> call, Throwable t) {

            }

        });
    }

    /**
     * Llamada al retrofit
     */

    private void iniciaRetrofit() {
        //iniciamos retrofit
        retrofit = new Retrofit.Builder()
                //base del servicio web
                .baseUrl("http://pokeapi.co/api/v2/")
                //conversor de JSON, existen otros(XML...)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //creamos el servicio web desde la interface creada anteriormente
        servicioWebPokemon = retrofit.create(WebServicePokeApi.class);
        //la lista inicial se encuentra vacía
        listaPokemonApi = new ArrayList<Pokemon>();
        listaPokemonApiLiveData = new MutableLiveData<>();

    }

    //*************************WebService******************************************

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

    //************************FINAL WebService***************************************


}


