package com.example.dam203.proyecto1eva.aplicacion;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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
import com.example.dam203.proyecto1eva.dialogos.UsuarioExistenteDialogo;

/**
 * Created by dam203 on 15/11/2017.
 */

public class EdicionPerfil extends AppCompatActivity {
    Usuario usr;
    UsuarioDAOSQLite usrDAO;
    EditText nombre;
    EditText login;
    EditText password;
    EditText email;
    Spinner direccion;
    CheckBox subscripcion;
    boolean estadoSubscripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_perfil);
        this.setTitle(R.string.editar_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usr = (Usuario) getIntent().getExtras().getSerializable(MainActivity.KEY_USUARIO);
        this.usrDAO = new UsuarioDAOSQLite(this);
        this.estadoSubscripcion = usr.getSubscripcion();
        inicializarVariables();
        cambiarValoresIniciales();
        gestionEventos();
    }

    void gestionEventos() {
        Button aceptar = findViewById(R.id.aceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuario = nombre.getText().toString().trim();
                String loginUsuario = login.getText().toString().trim();
                String passUsuario = password.getText().toString();
                String emailUsuario = email.getText().toString().trim();
                String direccionUsuario = getDireccion();
                if (direccionUsuario.equalsIgnoreCase("Other")){
                    direccionUsuario = "Otro";
                }
                try{
                    if (validar(nombreUsuario, loginUsuario,
                            passUsuario, emailUsuario, direccionUsuario)){
                        Usuario nuevoUsuario = new Usuario(nombreUsuario, loginUsuario, passUsuario, emailUsuario,
                                direccionUsuario, estadoSubscripcion, usr.getId());
                            usrDAO.modificarUsuario(nuevoUsuario);
                            Intent datos = new Intent(); // datos para devolver a aplicación principal
                            datos.putExtra("USUARIO", nuevoUsuario);
                            setResult(RESULT_OK, datos);
                            finish();
                    }
                }catch (SQLiteConstraintException ex){
                    Log.d("DEPURACIÓN", "Usuario ya existe en la DB.");
                    crearDialogUsuarioExistente();
                }
            }

        });
    }

    private void crearDialogUsuarioExistente(){
        UsuarioExistenteDialogo d = new UsuarioExistenteDialogo();
        FragmentManager fm = this.getSupportFragmentManager();
        d.show(fm, "Usuario ya existe");
    }

    private void cambiarValoresIniciales() {
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
        boolean validacionCamposVacios;
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
        Spinner spinner = findViewById(R.id.direccion);
        return spinner.getSelectedItem().toString();
    }

    public void comprobarCambioSubscripcion(View view){
        CheckBox checkBox = (CheckBox)view;
        this.estadoSubscripcion = checkBox.isChecked();
    }

}
