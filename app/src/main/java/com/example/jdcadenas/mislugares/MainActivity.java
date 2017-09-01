package com.example.jdcadenas.mislugares;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button bacercaDe;
    private Button bSalir;
    private Button bPreferencias;
    public static LugaresBD lugares;
    public MediaPlayer mp;
    private RecyclerView recyclerView;
    public static AdapatadorLugaresBD adaptador;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onPause() {
        Toast.makeText(this, "en pausa", Toast.LENGTH_SHORT).show();
        mp.pause();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lugares = new LugaresBD(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this, R.raw.audio);
        mp.start();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adaptador = new AdapatadorLugaresBD(this, lugares, lugares.extraeCursor());
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, VistaLugarActivity.class);
                i.putExtra("id", (long) recyclerView.getChildAdapterPosition(v));
                startActivity(i);
            }
        });

    }
    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this,AcercaDeActivity.class);
        startActivity(i);
    }
    public void lanzarPreferencias(View view){
        Intent i = new Intent(this,ConfigurarActivity.class);

        startActivity(i);
    }
    public void lanzarVistaLugar(View view){
        final EditText entrada = new EditText(this);
        entrada.setText("0");
        new AlertDialog.Builder(this)
                .setTitle("Selección de lugar")
                .setMessage("indica su id:")
                .setView(entrada)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        long id = Long.parseLong(entrada.getText().toString());
                        Intent i = new Intent(MainActivity.this,
                                VistaLugarActivity.class);
                        i.putExtra("id", id);
                        startActivity(i);
                    }})
                .setNegativeButton("Cancelar", null)
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.acercaDe){
            lanzarAcercaDe(null);
            return true;
        }
        if(id == R.id.action_settings){
            lanzarPreferencias(null);
            return true;
        }
        if(id == R.id.menu_buscar){
            lanzarVistaLugar(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
