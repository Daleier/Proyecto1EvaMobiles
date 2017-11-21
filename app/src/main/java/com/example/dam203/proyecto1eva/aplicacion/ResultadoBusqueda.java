package com.example.dam203.proyecto1eva.aplicacion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.example.dam203.proyecto1eva.R;

/**
 * Created by dam203 on 21/11/2017.
 */

public class ResultadoBusqueda extends AppCompatActivity {
    private Appcomponentes appc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String nuevoTitulo = "Resultado de la búsqueda";
        setTitle(nuevoTitulo);
        /*Recuperar el código SQL de la consulta construida en BusquedaVuelos*/
        Intent intent = getIntent();
        String consulta=intent.getExtras().getString(BusquedaComponentes.CONSULTA);
        this.appc = new Appcomponentes(getApplicationContext());
        SQLiteDatabase sqlLiteDB = appc.getWritableDatabase();
        Cursor cursor = sqlLiteDB.rawQuery(consulta, null);
        Log.d("DEPURACIÓN", "Nº filas: " + cursor.getCount());
        ListView resultadoVuelos = findViewById(R.id.listView);

        //Añadimos los datos al Adapter y le indicamos donde dibujar cada dato en la fila del Layout
        String[] desdeEstasColumnas = {"origen", "destino", "hsalida", "hllegada", "duracion", "precio"};
        int[] aEstasViews = {R.id.origen, R.id.destino, R.id.hora_salida, R.id.hora_llegada, R.id.duracion, R.id.precio};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.layout_elemento_lista_resultado, cursor, desdeEstasColumnas, aEstasViews, 0);
        resultadoVuelos.setAdapter(adapter);

        //Elemento de título sobre el ListView
        TextView torigen, tdestino;
        torigen= findViewById(R.id.torigen);
        tdestino= findViewById(R.id.tdestino);
        if (cursor.moveToFirst()){
            torigen.setText(cursor.getString(cursor.getColumnIndex("origen")));
            tdestino.setText(cursor.getString(cursor.getColumnIndex("destino")));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
