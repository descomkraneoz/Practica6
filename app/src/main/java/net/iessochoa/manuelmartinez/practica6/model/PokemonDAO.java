package net.iessochoa.manuelmartinez.practica6.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PokemonDAO {

    /*sentencia de ejemplo
    @Query("Select * from POKEMON where nombre")
    List<Pokemon> getAllPokemon();*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pokemon pokemon);
    @Delete
    void deleteByPokemon(Pokemon pokemon);
    @Query("DELETE FROM "+Pokemon.TABLE_NAME)
    void deleteAll();
    @Update
    void update(Pokemon pokemon);
    @Query("SELECT * FROM "+Pokemon.TABLE_NAME+" ORDER BY "+Pokemon.NOMBRE)
    LiveData<List<Pokemon>> allPokemon();

    /**
     * a. Un método que acepte como parámetro un string y que busque aquellos pokemon que su
     * nombre contenga el parámetro. Acuérdate de Like de SQL. Tenéis algo parecido en la
     * práctica de ejemplo
     * b. Un método que devuelva todos los pokemon ordenados por índice
     * c. Un método que devuelva todos los pokemon ordenados por fecha de compra
     */

    @Query("SELECT * FROM "+Pokemon.TABLE_NAME+" WHERE :nombre LIKE "+Pokemon.NOMBRE+" ORDER BY " +Pokemon.NOMBRE)
    LiveData<List<Pokemon>>getByNombre(String nombre);
}
