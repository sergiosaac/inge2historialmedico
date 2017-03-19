package com.example.usuario.miprimeraapp;

/**
 * Created by Usuario on 19/03/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Clase_BaseDatos extends SQLiteOpenHelper {

    public Clase_BaseDatos(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, nombre, factory, version);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        //aquí creamos la tabla de usuario (dni, nombre, ciudad, numero)
        db.execSQL("create table hijos(id integer primary key, nombre text, apellido text, fecha_nacimiento text, sexo text)");

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        db.execSQL("drop table if exists hijos");

        db.execSQL("create table hijos(id integer primary key, nombre text, apellido text, fecha_nacimiento text, sexo text)");

    }

}
