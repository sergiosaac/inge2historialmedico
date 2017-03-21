package com.example.usuario.miprimeraapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;

    private ListView list;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Manejador_sqlite helper = new Manejador_sqlite(this);
        Clase_BaseDatos admin = new Clase_BaseDatos(this,
                "administracion", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();


       /* ContentValues registro = new ContentValues();

        Random rnd = new Random();
        registro.put("id", rnd.nextInt());
        registro.put("nombre", "Sergio");
        registro.put("apellido", "Asuncion");
        registro.put("fecha_nacimiento", "43/93");
        registro.put("sexo", "masculino");

        // los inserto en la base de datos
        bd.insert("hijos", null, registro);

        bd.close();*/

        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        idTextView = (TextView) findViewById(R.id.idTextView);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        Cursor fila = bd.rawQuery(

                "select nombre, apellido from hijos", null);


        ArrayList<String> nombreArrayList = new ArrayList<String>();

        if (fila.moveToFirst()) {

            for (int i = 0; i < fila.getCount(); i++) {
                nombreArrayList.add(fila.getString(0));
            }

        } else

            Toast.makeText(this, "No existe ningún usuario con ese dni",

                    Toast.LENGTH_SHORT).show();

        list = (ListView)findViewById(R.id.listview);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombreArrayList);
        list.setAdapter(adaptador);

        this.crearTablaVacuna();
        this.leerTablaVacuna();

    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            nameTextView.setText(account.getDisplayName());
            emailTextView.setText(account.getEmail());
            idTextView.setText(account.getId());

            Glide.with(this).load(account.getPhotoUrl()).into(photoImageView);

        } else {
            goLogInScreen();
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revoke(View view) {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_revoke, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void crearTablaVacuna (){

        //db.execSQL("create table vacuna(id integer primary key, edad text, dosis text, fecha text, lote text, responsable text,id_paciente integer)");

        Clase_BaseDatos admin = new Clase_BaseDatos(this,
                "administracion", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        Random rnd = new Random();
        registro.put("id", rnd.nextInt());
        registro.put("edad", "7");
        registro.put("dosis", "1");
        registro.put("responsable", "Juan Perez");
        registro.put("id_paciente", 1);

        // los inserto en la base de datos
        bd.insert("vacuna", null, registro);

        bd.close();
    }

    public void leerTablaVacuna (){

        Clase_BaseDatos admin = new Clase_BaseDatos(this,
                "administracion", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery(

                "select responsable from vacuna", null);


        ArrayList<String> nombreArrayList = new ArrayList<String>();

        if (fila.moveToFirst()) {

            for (int i = 0; i < fila.getCount(); i++) {
                nombreArrayList.add(fila.getString(0));
            }

        } else

            Toast.makeText(this, "No hay vacunas",

                    Toast.LENGTH_SHORT).show();

        System.out.println("imprimir");
        System.out.println(nombreArrayList.toString());
    }
}