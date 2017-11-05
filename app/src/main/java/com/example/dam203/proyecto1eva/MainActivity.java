package com.example.dam203.proyecto1eva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {
    private appcomponentes appv;
    public static String NOMBRE = "nombre";
    public static String ID = "id";
    public static String LOGIN = "login";
    public static String PASSWORD = "password";
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
                + appcomponentes.NOME_BD;
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
            inputstream = getAssets().open(appcomponentes.NOME_BD);
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void xestionarEventos() {
        Button btnAbrirBD = (Button) findViewById(R.id.login_button);
        btnAbrirBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                iniciarSesion();
            }
        });
    }

    void iniciarSesion() {
        String login = ((EditText) findViewById(R.id.login)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        Usuario usr = this.usrDAO.getUsuario(login, password);

        if (usr != null) {
            Log.d("DEPURACIÓN", "Nombre usr: "+ usr.getNombre());
            this.NOMBRE = usr.getNombre();
            this.ID = Integer.toString(usr.getId());
            this.LOGIN = usr.getLogin();
            this.PASSWORD = usr.getPassword();
            Toast.makeText(getApplicationContext(),R.string.toast_login, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, BusquedaComponentes.class);
            intent.putExtra(this.NOMBRE, usr.getNombre());
            intent.putExtra(this.ID, Integer.toString(usr.getId()));
            intent.putExtra(this.PASSWORD, usr.getPassword());
            intent.putExtra(this.LOGIN, usr.getLogin());
            startActivity(intent);
            finish();
        } else {
            //Toast.makeText(getApplicationContext(), "Error de autentificación.", Toast.LENGTH_LONG).show();
            MensajeDialogo d= new MensajeDialogo();
            FragmentManager fm= this.getSupportFragmentManager();
            d.show(fm,"errorLogin");
        }
    }
}
