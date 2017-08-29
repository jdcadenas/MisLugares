package com.example.jdcadenas.mislugares;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Usuario on 27/08/2017.
 */

public class VistaLugarActivity extends AppCompatActivity {
    private long id;
    private Lugar lugar;
    final static int RESULTADO_EDITAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lugar);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id",0);

       actualizarVistas();

    }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.vista_lugar, menu);
      return true;
   }




   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.accion_compartir:
         return true;
      case R.id.accion_llegar:
         return true;
      case R.id.accion_editar:
          lanzarEdicion((int) id);
         return true;
      case R.id.accion_borrar:
          borrarLugar((int) id);

         return true;
      default:
         return super.onOptionsItemSelected(item);
      }
   }

   public void lanzarEdicion(final int id){
       Intent i = new Intent(this,EdicionLugarActivity.class);
       i.putExtra("id", id);
       startActivityForResult(i, RESULTADO_EDITAR);
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULTADO_EDITAR)
        {
            actualizarVistas();
            findViewById(R.id.scrollView1).invalidate();
        }
    }

    public void borrarLugar(final int id) {
       new AlertDialog.Builder(this)
               .setTitle("Borrado de lugar")
               .setMessage("Está seguro que quieres eliminar este lugar")
               .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       MainActivity.lugares.borrar((int) id);
                       finish();
                   }
               })
               .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                   }
               })
               .show();



    }
public void actualizarVistas(){
     lugar = MainActivity.lugares.elemento((int) id);
        TextView nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());
        ImageView logo_tipo = (ImageView) findViewById(R.id.logo_tipo);
        logo_tipo.setImageResource(lugar.getTipo().getRecurso());
        TextView tipo = (TextView) findViewById(R.id.tipo);
        tipo.setText(lugar.getTipo().getTexto());
        TextView direccion = (TextView) findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());
        TextView telefono = (TextView) findViewById(R.id.telefono);

        telefono.setText(Integer.toString(lugar.getTelefono()));
        TextView url = (TextView) findViewById(R.id.url);
        url.setText(lugar.getUrl());
        TextView comentario = (TextView) findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());
        TextView fecha = (TextView) findViewById(R.id.fecha);
        fecha.setText(DateFormat.getDateInstance().format(
            new Date(lugar.getFecha())));
        TextView hora = (TextView) findViewById(R.id.hora);
        hora.setText(DateFormat.getTimeInstance().format(
            new Date(lugar.getFecha())));
        RatingBar valoracion = (RatingBar) findViewById(R.id.valoracion);
        valoracion.setRating(lugar.getValoracion());
        valoracion.setOnRatingBarChangeListener(
            new RatingBar.OnRatingBarChangeListener() {
                @Override public void onRatingChanged(RatingBar ratingBar,
                                         float valor, boolean fromUser) {
                    lugar.setValoracion(valor);
                }
        });

}


}
