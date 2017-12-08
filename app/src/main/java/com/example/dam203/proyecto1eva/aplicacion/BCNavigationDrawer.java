package com.example.dam203.proyecto1eva.aplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dam203.proyecto1eva.R;

public class BCNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    private static final int COD_PETICION = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        usr = (Usuario) intent.getSerializableExtra(MainActivity.KEY_USUARIO);
        cambiarTituloVentana();
        inicializarVariables();
        gestionEventos();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
                startActivityForResult(intent, COD_PETICION);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COD_PETICION) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("USUARIO")) {
                    Usuario nuevoUsuario = (Usuario) data.getExtras().getSerializable("USUARIO");
                    cambiarUsuario(nuevoUsuario);
                    cambiarTituloVentana();
                }
            }
        }
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

    private void cambiarUsuario(Usuario usrNEW){
        usr = usrNEW;
    }

}
