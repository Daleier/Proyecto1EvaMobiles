package com.example.dam203.proyecto1eva.aplicacion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dam203.proyecto1eva.R;

/**
 * Created by dam203 on 15/11/2017.
 */

public class EdicionPerfil extends AppCompatActivity {
    Usuario usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_perfil);
        usr = (Usuario) getIntent().getExtras().getSerializable(MainActivity.KEY_USUARIO);
    }
}
