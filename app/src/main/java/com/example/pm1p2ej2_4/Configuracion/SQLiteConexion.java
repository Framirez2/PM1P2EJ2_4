package com.example.pm1p2ej2_4.Configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pm1p2ej2_4.Tablas.Transacciones;

public class SQLiteConexion extends SQLiteOpenHelper {
    //Constructor SQLite
    public SQLiteConexion(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version){

        super(context, dbname, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creacion de Objetos de la BD
        sqLiteDatabase.execSQL(Transacciones.createTableSignatures);//Creando tabla de Firmas
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Eliminamos la data y las tablas de la aplicacion
        sqLiteDatabase.execSQL(Transacciones.DropTableSignatures);
        onCreate(sqLiteDatabase);
    }
}
