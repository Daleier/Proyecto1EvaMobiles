package com.example.dam203.proyecto1eva.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dam203.proyecto1eva.R;

/**
 * Created by dam203 on 15/11/2017.
 */

public class EdicionPerfil extends AppCompatActivity {
    Usuario usr;
    EditText nombre;
    EditText login;
    EditText password;
    EditText email;
    Spinner direccion;
    CheckBox subscripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_perfil);
        usr = (Usuario) getIntent().getExtras().getSerializable(MainActivity.KEY_USUARIO);
        inicializarVariables();
        cambiarValores();
        gestionEventos();
    }

    void gestionEventos() {
        Button aceptar = findViewById(R.id.aceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO comprobar campos
                // TODO ejecutar consulta
            }
        });
    }

    private void cambiarValores() {
        nombre.setText(usr.getNombre());
        login.setText(usr.getLogin());
        password.setText(usr.getPassword());
        email.setText(usr.getEmail());
        for (int i = 0;i < direccion.getAdapter().getCount() ;i ++){ //itera en el numero de posiciones que tiene el spinner
            if(usr.getDireccion().equals(direccion.getItemAtPosition(i).toString())){ //comprueba que el valor del usuario sea igual al del spinner en esa posicion
                direccion.setSelection(i);
                break;
            }
        }
        subscripcion.setChecked(usr.getSubscripcion());
    }


    private void inicializarVariables() {
        nombre = findViewById(R.id.nombre);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        subscripcion = findViewById(R.id.subscripcion);
    }
}
