package com.example.usuario.miprimeraapp;

/**
 * Created by Usuario on 19/03/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Clase_BaseDatos extends SQLiteOpenHelper {

    public Clase_BaseDatos(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, nombre, factory, version);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {


        //db.execSQL("create table vacuna(id integer primary key, nombre text, apellido text, fecha_nacimiento text, sexo text)");
        db.execSQL("create table vacuna(id integer primary key, edad text, dosis text, fecha text, lote text, responsable text,id_paciente integer)");

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        db.execSQL("drop table if exits vacuna");

        db.execSQL("create table vacuna(id integer primary key, edad text, dosis text, fecha text, lote text, responsable text,id_paciente integer)");

        db.execSQL("drop table if exists hijos");

        db.execSQL("create table hijos(id integer primary key, nombre text, apellido text, fecha_nacimiento text, sexo text)");

    }

    public Datos recuperarDatos(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String valores_recuperar [] = {"_id", "nombre", "apellido", "fecha", "sexo"};
        Cursor c = db.query("datos",valores_recuperar,"_id=" + id, null, null, null, null, null);
        if(c != null) {
            c.moveToFirst();
        }
        Datos datos= new Datos(c.getInt(0), c.getString(1),
                c.getString(2), c.getString(3), c.getString(4));
        db.close();
        c.close();
        return datos;
    }

    public List<Datos> recuperarDatos() {
        SQLiteDatabase db = getReadableDatabase();
        List<Datos> lista_datos = new ArrayList<Datos>();
        String valores_recuperar [] = {"_id", "nombre", "apellido", "fecha", "sexo"};
        Cursor c = db.query("datos",valores_recuperar, null, null, null, null, null);
        c.moveToFirst();
        do {
            Datos datos = new Datos(c.getInt(0), c.getString(1),
                    c.getString(2), c.getString(3), c.getString(4));
            lista_datos.add(datos);
        } while (c.moveToNext());
        db.close();
        c.close();
        return lista_datos;
    }

}
