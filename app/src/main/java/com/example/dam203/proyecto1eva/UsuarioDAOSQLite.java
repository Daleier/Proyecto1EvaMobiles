package com.example.dam203.proyecto1eva;

/**
 * Created by dam203 on 31/10/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsuarioDAOSQLite implements UsuarioDAO {
    private appcomponentes appv;
    private Context context;

    UsuarioDAOSQLite(Context context){
        this.context=context;
        this.appv = new appcomponentes(this.context);
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

}
