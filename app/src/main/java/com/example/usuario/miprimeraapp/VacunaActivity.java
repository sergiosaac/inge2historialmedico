package com.example.usuario.miprimeraapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class VacunaActivity extends AppCompatActivity {


    private ListView list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacuna);

        Clase_BaseDatos admin = new Clase_BaseDatos(this,
                "admin", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();


        ContentValues detalles = new ContentValues();

        Random rnd = new Random();
        detalles.put("id", rnd.nextInt());
        detalles.put("edad", "2 meses");
        detalles.put("dosis", "primera");
        detalles.put("fecha", "02/05/17");
        detalles.put("responsable", "Lilian Marecos");
        detalles.put("id_paciente",rnd.nextInt());

        // los inserto en la base de datos
        bd.insert("vacuna", null, detalles);

        //bd.close();

        Cursor filavacuna = bd.rawQuery(

                "select edad, dosis from vacuna", null);

        ArrayList<String> nombre_ArrayList = new ArrayList<String>();

        if (filavacuna.moveToFirst()) {

            for (int i = 0; i < filavacuna.getCount(); i++) {
                nombre_ArrayList.add(filavacuna.getString(0));
            }

        } else

            Toast.makeText(this, "No existe ningún usuario con ese id",

                    Toast.LENGTH_SHORT).show();

        list2 = (ListView)findViewById(R.id.listview2);
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombre_ArrayList);
        list2.setAdapter(adaptador2);


    }
}