package com.example.dam203.proyecto1eva.aplicacion;

/**
 * Created by dam203 on 31/10/2017.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Appcomponentes extends SQLiteOpenHelper {
    public final static String NOME_BD = "appcomponentes.db";
    public final static int VERSION_BD = 1;

    public Appcomponentes(Context context) {
        super(context, NOME_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
