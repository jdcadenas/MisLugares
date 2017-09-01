package com.example.jdcadenas.mislugares;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Usuario on 29/08/2017.
 */

public class EdicionLugarActivity extends AppCompatActivity {

    private EditText nombre;
    private Spinner tipo;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;
    private long id;
    private Lugar lugar;
    private long _id;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancelarmenu:
                if (_id != -1) {
                    MainActivity.lugares.borrar((int) _id);
                }
                finish();
                return true;
            case R.id.guardar:
                guardar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void guardar() {
        lugar.setNombre(nombre.getText().toString());
        lugar.setTipo(TipoLugar.values()[tipo.getSelectedItemPosition()]);
        lugar.setDireccion(direccion.getText().toString());
        lugar.setTelefono(Integer.parseInt(telefono.getText().toString()));
        lugar.setUrl(url.getText().toString());
        lugar.setComentario(comentario.getText().toString());
        if (_id == -1) {
            _id = MainActivity.adaptador.idPosicion((int) id);
        }
        MainActivity.lugares.actualiza((int) _id, lugar);
        MainActivity.adaptador.setCursor(MainActivity.lugares.extraeCursor());

        if (id != -1) {
            MainActivity.adaptador.notifyItemChanged((int) id);
        } else {
            MainActivity.adaptador.notifyDataSetChanged();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edicion, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_lugar);


        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id", -1);
        _id = extras.getLong("_id", -1);
        if (_id != -1) {
            lugar = MainActivity.lugares.elemento((int) _id);
        } else {
            lugar = MainActivity.adaptador.lugarPosicion((int) id);
        }
        nombre = (EditText) findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());


        direccion = (EditText) findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());

        telefono = (EditText) findViewById(R.id.telefono);
        telefono.setText(Integer.toString(lugar.getTelefono()));

        url = (EditText) findViewById(R.id.url);
        url.setText(lugar.getUrl());

        comentario = (EditText) findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());

        tipo = (Spinner) findViewById(R.id.tipo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TipoLugar.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);
        tipo.setSelection(lugar.getTipo().ordinal());

    }
}
