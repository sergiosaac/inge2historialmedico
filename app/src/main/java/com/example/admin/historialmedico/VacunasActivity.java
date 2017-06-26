package com.example.admin.historialmedico;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VacunasActivity extends AppCompatActivity {

    private String TAG = VacunasActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    Button vacunas;


    private String idHijo;

    // URL to get contacts JSON
    private static String url = "http://10.9.100.164:8080/vacunas.json";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new VacunasActivity.obtenerVacunas().execute();

        this.idHijo = getIntent().getExtras().getString("idHijo");

        Button boton = (Button) findViewById(R.id.abc);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String host = "http://10.9.100.164:8080";

                HttpHandler nuevo = new HttpHandler();

                JSONObject obj = new JSONObject();
                try {
                    obj.put("idHijo", VacunasActivity.this.idHijo);
                    obj.put("order", "nombre");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String jsonStr = nuevo.sendHTTPData(host+"/WebApplication3/webresources/vacunas/obtenerVacunasPost/",obj);

                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONArray jsonObj = new JSONArray(jsonStr);

                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject c = jsonObj.getJSONObject(i);

                            String name = c.getString("nombre");
                            String email = c.getString("fecha_aplicacion");
                            String address = c.getString("aplicada");

                            HashMap<String, String> contact = new HashMap<>();

                            contact.put("name", name);
                            contact.put("email", email);
                            contact.put("mobile", "Inyectada: "+address);

                            contactList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "No se encontraron vacunas",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }

                Intent intent = getIntent();
                finish();
                startActivity(intent);

                Toast.makeText(getApplicationContext(),
                        "Ordenado alfabeticamente",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });

        Button fecha = (Button) findViewById(R.id.fecha);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String host = "http://10.9.100.164:8080";

                HttpHandler nuevo = new HttpHandler();

                JSONObject obj = new JSONObject();
                try {
                    obj.put("idHijo", VacunasActivity.this.idHijo);
                    obj.put("order", "fecha_aplicacion");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String jsonStr = nuevo.sendHTTPData(host+"/WebApplication3/webresources/vacunas/obtenerVacunasPost/",obj);

                Log.e(TAG, "Response from url: " + jsonStr);

                if (jsonStr != null) {
                    try {
                        JSONArray jsonObj = new JSONArray(jsonStr);

                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject c = jsonObj.getJSONObject(i);

                            String name = c.getString("nombre");
                            String email = c.getString("fecha_aplicacion");
                            String address = c.getString("aplicada");

                            HashMap<String, String> contact = new HashMap<>();

                            contact.put("name", name);
                            contact.put("email", email);
                            contact.put("mobile", "Inyectada: "+address);

                            contactList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "No se encontraron vacunas",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }

                Intent intent = getIntent();
                finish();
                startActivity(intent);

                Toast.makeText(getApplicationContext(),
                        "Ordenado por fecha",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class obtenerVacunas extends AsyncTask<Void, Void, Void> {

        public String host = "http://10.9.100.164:8080";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(VacunasActivity.this);
            pDialog.setMessage("Aguarda un momento...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler nuevo = new HttpHandler();

            JSONObject obj = new JSONObject();
            try {
                obj.put("idHijo", VacunasActivity.this.idHijo);
                obj.put("order", "id");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonStr = nuevo.sendHTTPData(this.host+"/WebApplication3/webresources/vacunas/obtenerVacunasPost/",obj);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray jsonObj = new JSONArray(jsonStr);

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject c = jsonObj.getJSONObject(i);

                        String name = c.getString("nombre");
                        String email = c.getString("fecha_aplicacion");
                        String address = c.getString("aplicada");

                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", "Inyectada: "+address);

                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "No se encontraron vacunas",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    VacunasActivity.this, contactList,
                    R.layout.list_item, new String[]{"name", "email",
                    "mobile"}, new int[]{R.id.name,
                    R.id.email, R.id.mobile});

            lv.setAdapter(adapter);
        }

    }





}
