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
    TextView email;
    TextView direccion;
    TextView subscripcion;
    TextView usr_id;
    Usuario usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_componentes);
        Intent intent = getIntent();
        usr = (Usuario) intent.getSerializableExtra("USUARIO");
        String nuevoTitulo= getString(R.string.identificador)
                +": "+usr.getNombre();
        setTitle(nuevoTitulo);
        iniciarVariables();
        completarCampos(intent);
    }

    private void iniciarVariables() {
        nombre = findViewById (R.id.nombre);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        subscripcion = findViewById(R.id.subscripcion);
        usr_id = findViewById(R.id.usr_id);
    }

    private void completarCampos(Intent intent) {
        login.setText(usr.getLogin());
        password.setText(usr.getPassword());
        email.setText(usr.getEmail());
        direccion.setText(usr.getDireccion());
        subscripcion.setText(Boolean.toString(usr.getSubscripcion()));
        usr_id.setText(Integer.toString(usr.getId()));
    }
}
