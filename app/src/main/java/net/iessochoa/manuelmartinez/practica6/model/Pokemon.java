package net.iessochoa.manuelmartinez.practica6.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = Pokemon.TABLE_NAME,
        indices = {@Index(value = {Pokemon.NOMBRE},unique = true)})
public class Pokemon implements Parcelable {

    /**
     *  constantes para ayudarnos a construir la tabla
     */

    public static final String TABLE_NAME="pokemon";
    public static final String ID= BaseColumns._ID;
    public static final String NOMBRE="nombre";
    public static final String uriIMAGEN="uri";
    public static final String FECHA_COMPRA="fechacompra";
    //base de la url donde se encuentra la imagen del pokemon
    public static final String urlIMAGEN="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    /**
     * Añidir las propiedades del pokemon, en las que además indicamos como
     * se llamarán las columnas en la base de datos y quien es la clave primaria
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(name=ID)
    private int id;
    @ColumnInfo(name = NOMBRE)
    @NonNull
    private String nombre;
    @ColumnInfo(name = uriIMAGEN)
    @NonNull
    private String uri;
    @ColumnInfo(name = FECHA_COMPRA)
    @NonNull
    private Date fechaCompra;

    /**
     *     constructor guarda el nombre del pokemon en mayúsculas y
     *     construye la dirección de la imagen del pokemon
     */

    public Pokemon(int id, @NonNull String nombre, @NonNull Date fechaCompra) {
        this.id = id;
        //ponemos en mayúsculas
        this.nombre = nombre.toUpperCase();
        //donde está la imagen
        this.uri = urlIMAGEN + id+ ".png";
        this.fechaCompra = fechaCompra;
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    protected Pokemon(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        uri = in.readString();
    }

    /**
     * getter y setter
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getUri() {
        return uri;
    }

    public void setUri(@NonNull String uri) {
        this.uri = uri;
    }

    @NonNull
    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(@NonNull Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    /**
     * método para que nos devuelva la fecha en formato local para
     * mostrar posteriormente la fecha.
     */

    public String getFechaCompraFormatoLocal(){
        //para mostrar la fecha en formato del idioma del dispositivo
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM,
                Locale.getDefault());
        return df.format(fechaCompra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeString(uri);
    }
}


