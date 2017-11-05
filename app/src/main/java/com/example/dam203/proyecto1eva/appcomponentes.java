package com.example.dam203.proyecto1eva;

/**
 * Created by dam203 on 31/10/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class appcomponentes extends SQLiteOpenHelper {
    public final static String NOME_BD = "appcomponentes.db";
    public final static int VERSION_BD = 1;

    public appcomponentes(Context context) {
        super(context, NOME_BD, null, VERSION_BD);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
