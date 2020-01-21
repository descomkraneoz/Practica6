package net.iessochoa.manuelmartinez.practica6.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Utils {
    /**
     * Nos devuelve el tipo de pantalla(SMALL, LARGE,....
     *
     * @param context
     * @return:Configuration.SCREENLAYOUT_SIZE_SMALL, Configuration.SCREENLAYOUT_SIZE_NORMAL...
     */
    public static int getTipoPantalla(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenLayout;
    }

    /**
     * Nos devuelve la orientacion de la pantalla:
     *
     * @param context
     * @return: Configuration.ORIENTATION_PORTRAIT,
     * Configuration.ORIENTATION_LANDSCAPE
     */
    public static int getOrientacionPantalla(Context context) {
        Display getOrient = ((Activity)
                context).getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        int width;
        int height;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = ((Activity)
                    context).getWindowManager().getDefaultDisplay();
            width = display.getWidth();
            height = display.getHeight();
        } else {
            Display display = ((Activity)
                    context).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        }
        if (width == height) {
            orientation = Configuration.ORIENTATION_SQUARE;
        } else {
            if (width < height) {
                //vertical
                orientation = Configuration.ORIENTATION_PORTRAIT;
            } else {//horizontal
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

    /**
     * Para comprobar el tipo de pantalla y nos permite
     * definir el número de columnas en el recyclerview.
     */

    public static void definirFormatoReciclerView(Context context, RecyclerView rcView) {
        int orientacionPantalla = getOrientacionPantalla(context);
        int tipoPantalla = getTipoPantalla(context);
        //pantalla pequeña y mediana
        if ((tipoPantalla == Configuration.SCREENLAYOUT_SIZE_SMALL)
                || (tipoPantalla == Configuration.SCREENLAYOUT_SIZE_NORMAL)) {
            if (orientacionPantalla == Configuration.ORIENTATION_PORTRAIT)//Vertical
                rcView.setLayoutManager(
                        new LinearLayoutManager(context,
                                LinearLayoutManager.VERTICAL, false));
            else
                rcView.setLayoutManager(new GridLayoutManager(context, 2));
        }//pantallas grandes
        else {
            if (orientacionPantalla == Configuration.ORIENTATION_LANDSCAPE)//horizontal
                rcView.setLayoutManager(new GridLayoutManager(context, 3));
            else
                rcView.setLayoutManager(new GridLayoutManager(context, 2));
        }
    }


}
