package com.example.dam203.proyecto1eva.aplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dam203.proyecto1eva.R;

/**
 * Created by dam203 on 31/10/2017.
 */

public class BusquedaComponentes extends AppCompatActivity {
    Usuario usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_componentes);
        Intent intent = getIntent();
        // coje el usuario que se ha logeado
        usr = (Usuario) intent.getSerializableExtra(MainActivity.KEY_USUARIO);
        String nuevoTitulo= getString(R.string.identificador)
                +": "+usr.getNombre();
        setTitle(nuevoTitulo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logout:
                //Ir a la ventana de inicio de sesión y finalizar la Activity.
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.editar_perfil:
                intent = new Intent(this, EdicionPerfil.class);
                intent.putExtra(MainActivity.KEY_USUARIO, usr);
                startActivity(intent);
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        return true;
    }


}
