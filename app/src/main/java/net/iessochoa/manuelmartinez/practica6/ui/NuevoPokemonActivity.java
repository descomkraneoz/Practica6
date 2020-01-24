package net.iessochoa.manuelmartinez.practica6.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import net.iessochoa.manuelmartinez.practica6.R;
import net.iessochoa.manuelmartinez.practica6.adapters.PokemonApiAdapter;
import net.iessochoa.manuelmartinez.practica6.model.Pokemon;
import net.iessochoa.manuelmartinez.practica6.utils.Utils;
import net.iessochoa.manuelmartinez.practica6.viewmodels.PokemonApiViewModel;

import java.util.List;

public class NuevoPokemonActivity extends AppCompatActivity {
    private static final String EXTRA_POKEMON = "net.iessochoa.manuelmartinez.practica6.ui.extrapokemon";

    private PokemonApiViewModel pokemonViewModel;
    private RecyclerView rvListaPokemon;
    private PokemonApiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pokemon);
        //RECYCLER_VIEW
        rvListaPokemon = findViewById(R.id.rvListaPokemon);
        //creamos el adaptador
        adapter = new PokemonApiAdapter();
        adapter.setOnItemComprarClickListener(new PokemonApiAdapter.onItemClickComprarListener() {
            @Override
            public void onItemComprarClick(Pokemon pokemon) {

            }
        });
        rvListaPokemon.setAdapter(adapter);
        Utils.definirFormatoReciclerView(this, rvListaPokemon);
        //VIEW_MODEL
        //Recuperamos el ViewModel
        pokemonViewModel = ViewModelProviders.of(this).get(PokemonApiViewModel.class);
        //Este livedata nos permite ver todos los contactos y en caso de que haya un
        //cambio en la base de datos, se mostrará automáticamente
        pokemonViewModel.getListaPokemonApi().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                //actualizamos el RecyclerView con la nueva lista
                adapter.setListaPokemon(pokemons);
            }
        });

    }
}
