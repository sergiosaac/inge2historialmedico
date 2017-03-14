package com.example.usuario.miprimeraapp;

/**
 * Created by Usuario on 13/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

import static android.provider.BaseColumns._ID;

public class Manejador_sqlite extends SQLiteOpenHelper {

    public Manejador_sqlite (Context ctx){
        super (ctx, "Mi base", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE Hijos ("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
             "nombre TEXT, apellido TEXT, fecha_nacimiento DATE, sexo TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nueva){
        db.execSQL("DROP TABLE IF EXITS Hijos");
        onCreate(db);
    }

    public void insertarReg(String nombre, String apellido, String fecha_nacimiento, String sexo){
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("apellido", apellido);
        valores.put("nacimiento", fecha_nacimiento);
        valores.put("sexo", sexo);
        this.getWritableDatabase().insert("niño",null,valores);
    }

    public String[] leer(){
        String result[] = new String [5];
        String columnas [] = {_ID, "nombre", "apellido", "nacimiento", "sexo"};
        Cursor c = this.getReadableDatabase().query("niño",columnas,null,null,null,null,null,null);

        int id,nom,ape,nac,sexo;
        id = c.getColumnIndex(_ID);
        nom = c.getColumnIndex("nombre");
        ape = c.getColumnIndex("apellido");
        nac = c.getColumnIndex("nacimiento");
        sexo = c.getColumnIndex("sexo");

        int contador = 0;
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result[contador] = c.getString(id)+" "+c.getString(nom)+
                    " "+c.getString(ape)+" "+c.getString(nac)+
                    " "+c.getString(sexo);

            contador++;


        }
        return result;
    }


    public void abrir(){
        this.getWritableDatabase();
    }

    public void cerrar(){
        this.close();
    }
}



