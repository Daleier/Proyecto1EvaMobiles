package com.example.dam203.proyecto1eva;

/**
 * Created by dam203 on 31/10/2017.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class UsuarioDAOSQLite implements UsuarioDAO {
    private Appcomponentes appv;
    private Context context;

    UsuarioDAOSQLite(Context context){
        this.context=context;
        this.appv = new Appcomponentes(this.context);
    }

    public Usuario getUsuario(String login, String password) {
        Usuario resultado = null;
        SQLiteDatabase sqlLiteDB = appv.getWritableDatabase();
        String[] param = {login, password};
        String consulta = "SELECT * FROM usuarios WHERE login=? AND password=?";
        Cursor cursor = sqlLiteDB.rawQuery(consulta, param);
        this.depuracion(consulta, param);
        Log.d("DEPURACIÓN", "Nº filas: " + cursor.getCount());
        if (cursor.moveToFirst()) {
            resultado = new Usuario(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        }
        return resultado;
    }

    void depuracion(String consulta, String[] param) {
        String texto = "Consulta: " + consulta + " Valores: ";
        for (String p : param) {
            texto += p + " ";
        }
        Log.d("DEPURACIÓN", texto);
    }

    public boolean insertarUsuario(Usuario usr) {
        boolean resultado = true;
        SQLiteDatabase sqlLiteDB = appv.getWritableDatabase();
        String sql = "INSERT INTO Usuario (nombre, login, password) VALUES (?, ?, ?)";
        SQLiteStatement statement = sqlLiteDB.compileStatement(sql);

        statement.bindString(1, usr.getNombre());
        statement.bindString(2, usr.getLogin());
        statement.bindString(3, usr.getPassword());

        long rowId = statement.executeInsert();

        if (rowId != -1) {
            //Comprobación de la lista de usuarios. El siguiente código tiene como finalidad
            //mostrar en el logcat el usuario que se acaba de insertar.
            String usuarios = "";
            Cursor cursor = sqlLiteDB.rawQuery("select * from Usuario", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    usuarios += " || " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3);
                    cursor.moveToNext();
                }
            }
            Log.d("DEPURACIÓN", "Resultado inserción: " + usuarios);
            Log.d("DEPURACIÓN", "Row ID: " + rowId);
        } else {
            resultado = false;
        }
        return resultado;
    }


}
