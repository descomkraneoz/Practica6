package net.iessochoa.manuelmartinez.practica6.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.iessochoa.manuelmartinez.practica6.R;
import net.iessochoa.manuelmartinez.practica6.model.Pokemon;

import java.util.List;

public class PokemonApiAdapter extends RecyclerView.Adapter<PokemonApiAdapter.PokemonViewHolder> {

    private List<Pokemon> listaPokemon;
    private onItemClickComprarListener listener;


    @NonNull
    @Override
    public PokemonApiAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                  int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemonapi,
                        parent, false);
        return new PokemonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonApiAdapter.PokemonViewHolder holder,
                                 int position) {
        if (listaPokemon != null) {
            //mostramos los valores en el cardview
            final Pokemon pokemon = listaPokemon.get(position);
            holder.tvNombre.setText(pokemon.getNombre());

            //utilizamos Glide para mostrar la imagen. Lo vemos más abajo
            cargaImagen(holder.ivImagenPokemon, pokemon.getUri());
            //guardamos el pokemon actual
            holder.pokemon = pokemon;
        }
    }

    private void cargaImagen(ImageView ivImagenPokemon, String uri) {
        Glide
                .with(ivImagenPokemon.getContext())
                //url de la imagen
                .load(uri)
                //centramos la imagen
                .centerCrop()
                //mientras se carga la imagen que imagen queremos mostrar
                .placeholder(android.R.drawable.stat_notify_sync_noanim)
                //Imagen que mostramos si hay error
                .error(android.R.drawable.ic_lock_lock)
                //donde colocamos la imagen
                .into(ivImagenPokemon);
    }


    @Override
    public int getItemCount() {
        if (listaPokemon != null)
            return listaPokemon.size();
        else return 0;
    }

    //cuando se modifique la base de datos, actualizamos el recyclerview
    public void setListaPokemon(List<Pokemon> pokemons) {
        listaPokemon = pokemons;
        notifyDataSetChanged();
    }

    public void setOnItemComprarClickListener(onItemClickComprarListener listener) {
        this.listener = listener;
    }

    public interface onItemClickComprarListener {
        void onItemComprarClick(Pokemon pokemon);
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        private ImageView ivImagenPokemon;
        private ImageView ivComprar;
        //guardamos el pokemon
        private Pokemon pokemon;

        public PokemonViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            ivImagenPokemon = itemView.findViewById(R.id.ivImagenPokemon);
            ivComprar = itemView.findViewById(R.id.ivComprar);
            //creamos el listener para el botón de comprar
            ivComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemComprarClick(pokemon);
                    }
                }
            });
        }

        public Pokemon getPokemon() {
            return pokemon;
        }

    }


}
