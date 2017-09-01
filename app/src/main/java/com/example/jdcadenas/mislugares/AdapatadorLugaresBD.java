package com.example.jdcadenas.mislugares;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Usuario on 31/08/2017.
 */

public class AdapatadorLugaresBD extends AdaptadorLugares {
    protected Cursor cursor;

    public AdapatadorLugaresBD(Context contexto, Lugares lugares, Cursor cursor) {
        super(contexto, lugares);
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Lugar lugarPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return LugaresBD.extraeLugar(cursor);
    }

    public int idPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lugar lugar = lugarPosicion(position);
        personalizaVista(holder, lugar);
    }
}
