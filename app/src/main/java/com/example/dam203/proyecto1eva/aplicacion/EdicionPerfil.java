package com.example.dam203.proyecto1eva.aplicacion;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dam203.proyecto1eva.R;
import com.example.dam203.proyecto1eva.dialogos.DireccionInvalidaDialogo;
import com.example.dam203.proyecto1eva.dialogos.PassInvalidaDialogo;
import com.example.dam203.proyecto1eva.dialogos.RegistroDialogo;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                if (validar(nombre.getText().toString().trim(), login.getText().toString().trim(),
                        password.getText().toString(), email.getText().toString().trim(), getDireccion())){
                    // TODO ejecutar consulta actualizacion en clase usuariosdaosql
                }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validar(String nombre, String login, String password, String email, String direccion) {
        boolean validacionCamposVacios = false;
        boolean validacionDireccion = false;
        boolean validacionPassword = false;
        //validacion
        validacionCamposVacios = validarCamposVacios(nombre, login, password, email);
        if(validacionCamposVacios) {
            validacionDireccion = validarDireccion(direccion);
        }
        if (validacionCamposVacios && validacionDireccion){
            validacionPassword = validarPassword(password);
        }
        return validacionCamposVacios && validacionDireccion && validacionPassword;
    }

    private boolean validarCamposVacios(String nombre, String login, String password, String email){
        if (nombre.isEmpty() || login.isEmpty() || password.isEmpty() || email.isEmpty()){
            RegistroDialogo d = new RegistroDialogo();
            FragmentManager fm = this.getSupportFragmentManager();
            d.show(fm, "errorRegistro");
            Log.d("DEPURACIÓN", "Algun campo esta vacio.");
            return false;
        }else{
            return true;
        }
    }

    private boolean validarDireccion(String direccion){
        if(direccion.equals("Direccion")) {
            DireccionInvalidaDialogo d = new DireccionInvalidaDialogo();
            FragmentManager fm= this.getSupportFragmentManager();
            d.show(fm,"errorDireccion");
            Log.d("DEPURACIÓN", "Campo Direccion seleccionado en la seleccion de paises.");
            return false;
        }else{
            return true;
        }
    }

    private boolean validarPassword(String password){
        final int MIN_CHAR_PASS = 6;
        if (password.length() < MIN_CHAR_PASS){
            PassInvalidaDialogo d = new PassInvalidaDialogo();
            FragmentManager fm= this.getSupportFragmentManager();
            d.show(fm,"errorPassword");
            Log.d("DEPURACIÓN", "Contraseña no valida.");
            return false;
        } else{
            return true;
        }
    }

    private String getDireccion() {
        Spinner spinner = findViewById(R.id.registro_direccion);
        return spinner.getSelectedItem().toString();
    }
}
