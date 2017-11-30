package com.example.dam203.proyecto1eva.aplicacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dam203.proyecto1eva.R;
import com.example.dam203.proyecto1eva.dialogos.LoginDialogo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {
    private Appcomponentes appv;
    protected static final String KEY_USUARIO ="USUARIO";
    Usuario usuario;
    UsuarioDAOSQLite usrDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        copiarBD();
        this.usrDAO = new UsuarioDAOSQLite(this);
        xestionarEventos();

    }

    private void copiarBD() {
        String bddestino = "/data/data/" + getPackageName() + "/databases/"
                + Appcomponentes.NOME_BD;
        File file = new File(bddestino);
        Log.d("DEPURACIÓN", "Ruta archivo BD: " + bddestino);
        if (file.exists()) {
            Toast.makeText(getApplicationContext(), R.string.toast_bd_existente, Toast.LENGTH_LONG).show();
            return; // XA EXISTE A BASE DE DATOS
        }

        String pathbd = "/data/data/" + getPackageName()
                + "/databases/";
        File filepathdb = new File(pathbd);
        filepathdb.mkdirs();

        InputStream inputstream;
        try {
            inputstream = getAssets().open(Appcomponentes.NOME_BD);
            OutputStream outputstream = new FileOutputStream(bddestino);

            int tamread;
            byte[] buffer = new byte[2048];

            while ((tamread = inputstream.read(buffer)) > 0) {
                outputstream.write(buffer, 0, tamread);
            }

            inputstream.close();
            outputstream.flush();
            outputstream.close();
            Toast.makeText(getApplicationContext(),R.string.toast_bd_copiada, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void xestionarEventos() {
        Button btnAbrirBD = findViewById(R.id.login_button);
        btnAbrirBD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        Button btnNuevoUsuario = findViewById(R.id.signup_button);
        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nuevoUsuario();
            }
        });
    }

    void iniciarSesion() {
        String login = ((EditText) findViewById(R.id.login)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        Usuario usr = this.usrDAO.getUsuario(login, password);

        if (usr != null) {
            Log.d("DEPURACIÓN", "Nombre usr: "+ usr.getNombre());
            // asigna valores campos usuarios
//            NOMBRE = usr.getNombre();
//            LOGIN = usr.getLogin();
//            PASSWORD = usr.getPassword();
//            EMAIL = usr.getEmail();
//            DIRECCION = usr.getDireccion();
//            SUBSCRIPCION = Boolean.toString(usr.getSubscripcion());
//            ID = Integer.toString(usr.getId());
            Toast.makeText(getApplicationContext(),R.string.toast_login, Toast.LENGTH_LONG).show();

            // crea nuevo intent
            Intent intent = new Intent(this, BusquedaComponentes.class);
            intent.putExtra(KEY_USUARIO, usr);
            startActivity(intent);
            finish();
        } else {
            //Toast.makeText(getApplicationContext(), "Error de autentificación.", Toast.LENGTH_LONG).show();
            LoginDialogo d= new LoginDialogo();
            FragmentManager fm= this.getSupportFragmentManager();
            d.show(fm,"errorLogin");
        }
    }

    void nuevoUsuario(){

        Toast.makeText(getApplicationContext(), "Formulario de registro de nuevo usuario.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, NuevoUsuario.class);
        startActivity(intent);
        // No se finaliza la Activity en este caso.
    }

    /**
     * Inicia sesion al pulsar el boton de acción en el campo contraseña del layout
     * @param view
     */
    public void iniciarSesionTeclado(View view) {
        iniciarSesion();
    }
}
