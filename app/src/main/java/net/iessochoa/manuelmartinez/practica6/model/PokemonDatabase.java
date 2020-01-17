package net.iessochoa.manuelmartinez.practica6.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {Pokemon.class}, version = 1)
//Nos transforma automáticamente las fechas a entero
@TypeConverters({TransformaFechaSQLite.class})
public abstract class PokemonDatabase extends RoomDatabase {
    //Permite el acceso a los metodos CRUD
    public abstract PokemonDAO pokemonDao();
    //la base de datos
    private static volatile PokemonDatabase INSTANCE;
    //singleton
    public static PokemonDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    //nombre del fichero de la base de datos
                                    PokemonDatabase.class, "pokemon_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
