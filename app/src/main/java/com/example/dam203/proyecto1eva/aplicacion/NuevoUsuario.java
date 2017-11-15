package com.example.dam203.proyecto1eva.aplicacion;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dam203.proyecto1eva.R;
import com.example.dam203.proyecto1eva.dialogos.DireccionInvalidaDialogo;
import com.example.dam203.proyecto1eva.dialogos.PassInvalidaDialogo;
import com.example.dam203.proyecto1eva.dialogos.RegistroDialogo;
import com.example.dam203.proyecto1eva.dialogos.UsuarioExistenteDialogo;

/**
 * Created by dam203 on 07/11/2017.
 */

public class NuevoUsuario extends AppCompatActivity {
    private Appcomponentes appv;
    UsuarioDAOSQLite usrDAO;
    private boolean subscripcion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        setTitle(R.string.titulo_crear_usuario);
        subscripcion = false;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.usrDAO = new UsuarioDAOSQLite(this);
        xestionarEventos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean resultado = false;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                resultado = super.onOptionsItemSelected(item);

        }
        return resultado;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    void xestionarEventos() {
        Button btnNuevoUsuario = findViewById(R.id.crear_usuario);
        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nombre = ((EditText) findViewById(R.id.registro_nombre)).getText().toString().trim();
                String login = ((EditText) findViewById(R.id.registro_login)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.registro_password)).getText().toString();
                String email = ((EditText) findViewById(R.id.registro_email)).getText().toString().trim();
                String direccion = getDireccion();

                if (validar(nombre, login, password, email, direccion)){
                    try {
                        crearUsuario(nombre, login, password, email, direccion);
                    }catch (SQLiteConstraintException ex){
                        Log.d("DEPURACIÓN", "Usuario ya existe en la DB.");
                        crearDialogUsuarioExistente();
                    }
                }
            }
        });
    }

    private void crearDialogUsuarioExistente(){
        UsuarioExistenteDialogo d = new UsuarioExistenteDialogo();
        FragmentManager fm = this.getSupportFragmentManager();
        d.show(fm, "Usuario ya existe");
    }

    void crearUsuario(String nombre, String login, String password, String email, String direccion) {
            /*Creación el objeto usuario. Dado que id es autoincrementable en la base de datos
            el valor del campo id no será procesado en el método de inserción de usuario.
            Por lo tanto, se le pasará 0, o cualquier otro valor.*/
            Usuario usr = new Usuario(nombre, login, password, email, direccion, subscripcion, 0);
            boolean insercion = this.usrDAO.insertarUsuario(usr);
            //Notificación de la inserción.
            if (insercion)
                Toast.makeText(getApplicationContext(), R.string.registro_exitoso, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), R.string.registro_fallido, Toast.LENGTH_LONG).show();
            //Ir a la ventana de inicio de sesión y finalizar la Activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
    }

    public void comprobarSubscripcion(View view) {
        CheckBox checkBox = (CheckBox)view;
        this.subscripcion = checkBox.isChecked();
    }

    private String getDireccion() {
        Spinner spinner = findViewById(R.id.registro_direccion);
        return spinner.getSelectedItem().toString();
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
}

