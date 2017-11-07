package com.example.dam203.proyecto1eva;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by dam203 on 31/10/2017.
 */

public class BusquedaComponentes extends AppCompatActivity {
    TextView nombre;
    TextView login;
    TextView password;
    TextView usr_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_componentes);
        Intent intent = getIntent();
        String nuevoTitulo= getString(R.string.identificador)
                +": "+intent.getExtras().getString(MainActivity.NOMBRE);
        setTitle(nuevoTitulo);
        iniciarVariables();
        completarCampos(intent);
    }

    private void iniciarVariables() {
        nombre = findViewById (R.id.nombre);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        usr_id = findViewById(R.id.user_id);
    }

    private void completarCampos(Intent intent) {
        nombre.setText(intent.getExtras().getString(MainActivity.NOMBRE));
        login.setText(intent.getExtras().getString(MainActivity.LOGIN));
        password.setText(intent.getExtras().getString(MainActivity.PASSWORD));
        usr_id.setText((intent.getExtras().getString(MainActivity.ID)));

    }
}
