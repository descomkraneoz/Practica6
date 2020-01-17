package net.iessochoa.manuelmartinez.practica6.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.iessochoa.manuelmartinez.practica6.R;
import net.iessochoa.manuelmartinez.practica6.adapters.PokemonAdapter;
import net.iessochoa.manuelmartinez.practica6.model.Pokemon;
import net.iessochoa.manuelmartinez.practica6.viewmodels.PokemonViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PokemonViewModel pokemonViewModel;
    private FloatingActionButton fabNuevo;
    private RecyclerView rvListaPokemon;
    private PokemonAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //RECYCLER_VIEW
        rvListaPokemon = findViewById(R.id.rvListaPokemon);
        //creamos el adaptador
        adapter = new PokemonAdapter();
        rvListaPokemon.setAdapter(adapter);
        rvListaPokemon.setLayoutManager(new LinearLayoutManager(this));

        //VIEW_MODEL
        //Recuperamos el ViewModel
        pokemonViewModel= ViewModelProviders.of(this).get(PokemonViewModel.class);

        //Este livedata nos permite ver todos los pokemon y en caso de que haya un cambio en la
        //base de datos, se mostrará automáticamente
        pokemonViewModel.getAllPokemons().observe(this, new Observer<List<Pokemon>>(){
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                //actualizamos el RecyclerView con la nueva lista
                adapter.setListaPokemon(pokemons);
            }
        });

        //ACCION DE BORRADO
        //asignamos la acción de borrado de elemento al recycler view. Fijaros como hemos creado
        //un nuevo objeto que implementa nuestra interface. Al borrar el elemento se muestra automáticamente
        //gracias al observer anterior

        adapter.setOnItemBorrarClickListener(new PokemonAdapter.onItemClickBorrarListener() {
            @Override
             public void onItemBorrarClick(Pokemon pokemon) {
                pokemonViewModel.delete(pokemon);
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_prueba_pokemon:
                cargaPokemonEjemplo();
                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    private void cargaPokemonEjemplo(){
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Pokemon pokemon;
        try {
            pokemon=new Pokemon(1,"bulbasaur",formatoDelTexto.parse("10-10-2019"));
                    pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(2,"ivysaur",formatoDelTexto.parse("11-10-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(3,"venusaur",formatoDelTexto.parse("12-11-2019"));
                    pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(4,"charmander",formatoDelTexto.parse("12-9-2019"));
                    pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(5,"charmeleon",formatoDelTexto.parse("12-5-2019"));
                    pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(6,"charizard",formatoDelTexto.parse("8-3-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(7,"squirtle",formatoDelTexto.parse("1-1-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(8,"wartortle",formatoDelTexto.parse("13-3-2019"));
                    pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(9,"blastoise",formatoDelTexto.parse("16-4-2019"));
                    pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(10,"caterpie",formatoDelTexto.parse("2-5-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(11,"metapod",formatoDelTexto.parse("6-7-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(12,"butterfree",formatoDelTexto.parse("20-2-2019"));
                    pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(13,"weedle",formatoDelTexto.parse("20-1-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(14,"kakuna",formatoDelTexto.parse("20-3-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(15,"beedrill",formatoDelTexto.parse("20-4-2019"));
                    pokemonViewModel.insert(pokemon);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
