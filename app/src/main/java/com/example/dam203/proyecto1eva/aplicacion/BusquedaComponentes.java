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
import android.widget.Toast;

import com.example.dam203.proyecto1eva.R;

/**
 * Created by dam203 on 31/10/2017.
 */

public class BusquedaComponentes extends AppCompatActivity {
    static Usuario usr;
    public final static String CONSULTA = "consulta";
    Spinner tipo_componente;
    EditText nombre_componente;
    EditText precio_min;
    EditText precio_max;
    String nombre = "";
    String tipo = "TODOS";
    double minimo = 0;
    double maximo = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_componentes);
        Intent intent = getIntent();
        // coje el usuario que se ha logeado
        usr = (Usuario) intent.getSerializableExtra(MainActivity.KEY_USUARIO);
        cambiarTituloVentana();
        inicializarVariables();
        gestionEventos();
    }

    private void cambiarTituloVentana() {
        String nuevoTitulo= getString(R.string.identificador)
                +": "+usr.getNombre();
        setTitle(nuevoTitulo);
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
                    buscarComponentes(nombre, tipo, minimo, maximo);
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
            Toast.makeText(getApplicationContext(), R.string.error_precios, Toast.LENGTH_LONG).show();
            return false;
        }
        if(minimo<0 || maximo < 0){ //en teoria este error nunca deberia saltar al usarse un teclado numerico
            Toast.makeText(getApplicationContext(), R.string.error_precio_negativo, Toast.LENGTH_LONG).show();
        }else if(minimo>maximo){
            Toast.makeText(getApplicationContext(), R.string.error_precio_mayor, Toast.LENGTH_LONG).show();
            return false;
        }
        tipo = tipo_componente.getSelectedItem().toString();
        return true;
    }

    void buscarComponentes(String nombre, String tipo, Double minimo, Double maximo) {
        /*Es necesario añadir a la consulta un campo _id para poder construir
        * en ResultadoBusqueda un ListAdapter directametne a partir del
        * resultado de la consulta*/
        String query = "";
        if(tipo.equalsIgnoreCase("ALL")){
            tipo = "TODOS";
        }
        if(nombre.isEmpty()){ //busqueda sin nombre
            if(tipo.equalsIgnoreCase("TODOS")){
                query = String.format("SELECT id AS _id, nombre, fabricante, tipo, descripcion||' - 29BPDJ' AS descripcion, precio||'€' AS precio " +
                        "FROM componentes " +
                        "WHERE precio BETWEEN %1$s AND %2$s", minimo.toString(), maximo.toString());
            }else {
                query = String.format("SELECT id AS _id, nombre, fabricante, tipo, descripcion||' - 29BPDJ' AS descripcion, precio||'€' AS precio " +
                        "FROM componentes " +
                        "WHERE (precio BETWEEN %1$s AND %2$s) AND tipo = '%3$s'", minimo.toString(), maximo.toString(), tipo);
            }
        }else{ //busqueda con nombre
            if(tipo.equalsIgnoreCase("TODOS")){
                query = String.format("SELECT id AS _id, nombre, fabricante, tipo, descripcion||' - 29BPDJ' AS descripcion, precio||'€' AS precio " +
                        "FROM componentes " +
                        "WHERE (precio BETWEEN %1$s AND %2$s) AND nombre LIKE '%3$s'", minimo.toString(), maximo.toString(), nombre);
            }else{
                query = String.format("SELECT id AS _id, nombre, fabricante, tipo, descripcion||' - 29BPDJ' AS descripcion, precio||'€' AS precio " +
                        "FROM componentes " +
                        "WHERE (precio BETWEEN %1$s AND %2$s) AND tipo = '%3$s' AND nombre LIKE '%4$s'", minimo.toString(), maximo.toString(), tipo, nombre);
            }
        }

        Log.d("DEPURACIÓN", "Consulta: " + query);
        //Ir a la Activity Resultado de la búsqueda. No se finaliza BusquedaComponentes.
        Intent intent = new Intent(this, ResultadoBusqueda.class);
        intent.putExtra(CONSULTA, query);

        startActivity(intent);
    }

    public static void cambiarUsuario(Usuario usrNEW){
        usr = usrNEW;
    }



}
