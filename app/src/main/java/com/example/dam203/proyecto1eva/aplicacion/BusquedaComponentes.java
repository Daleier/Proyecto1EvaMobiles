package com.example.dam203.proyecto1eva.aplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dam203.proyecto1eva.R;

/**
 * Created by dam203 on 31/10/2017.
 */

public class BusquedaComponentes extends AppCompatActivity {
    Usuario usr;
    public final static String CONSULTA = "consulta";
    Spinner tipo_componente;
    EditText nombre_componente;
    EditText precio_min;
    EditText precio_max;
    String nombre = "TODOS";
    String tipo;
    double minimo = 0;
    double maximo = 2500;
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
        inicializarVariables();
        gestionEventos();
    }

    private void inicializarVariables() {
        tipo_componente =  findViewById(R.id.spinner_tipo);
        nombre_componente =  findViewById(R.id.nombre_componente);
        precio_min =  findViewById(R.id.precio_min);
        precio_max =  findViewById(R.id.precio_max);
    }

    void gestionEventos() {
        Button btn_buscar = findViewById(R.id.buscar);
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()){
                    buscarVuelos(nombre, tipo, minimo, maximo);
                }
            }
        });
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

    private boolean validar(){
        String nombre = nombre_componente.getText().toString().trim();
        try {
            minimo = Double.parseDouble(precio_min.getText().toString().trim());
            maximo = Double.parseDouble(precio_max.getText().toString().trim());
        }catch (Exception ex){
            //TODO dialogo error conversion
            return false;
        }
        if(minimo>maximo){
            //TODO error precio minimo mayor que maximo
            return false;
        }
        tipo = tipo_componente.getSelectedItem().toString();
        return true;
    }

    void buscarVuelos(String nombre, String tipo, Double minimo, Double maximo) {
        /*Es necesario añadir a la consulta un campo _id para poder construir
        * en ResultadoBusqueda un ListAdapter directametne a partir del
        * resultado de la consulta*/
        String query = "";
        //TODO añadir consultas a querys
        if(nombre.isEmpty()){ //busqueda sin nombre
            if(tipo.equalsIgnoreCase("TODOS")){
                query ="";
            }else{
                query = "";
            }
        }else{ //busqueda con nombre
            if(tipo.equalsIgnoreCase("TODOS")){
                query ="";
            }else{
                query = "";
            }

        }

        Log.d("DEPURACIÓN", "Consulta: " + query);
        //Ir a la Activity Resultado de la búsqueda. No se finaliza BusquedaComponentes.
        Intent intent = new Intent(this, ResultadoBusqueda.class);
        intent.putExtra(CONSULTA, query);

        startActivity(intent);
    }




}
