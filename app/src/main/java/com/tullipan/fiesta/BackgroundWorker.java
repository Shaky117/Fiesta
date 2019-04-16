package com.tullipan.fiesta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

//Imagen url = URLBAse/img/uploads/ + nombreArchivo
public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String type;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];

        if (type.equals("categorias")) {
            try {
                String login_url = "http://fiesta.mawetecnologias.com/api/categorias";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("busqueda")) {
            try {
                String busqueda = params[1];
                String categoria = params[2];
                String evento = params[3];
                String pagina = params[4];
                String login_url = "http://fiesta.mawetecnologias.com/api/proveedores" +
                        "?q=" + busqueda +
                        "&cat=" + categoria +
                        "&evt=" + evento +
                        "&pag=" + pagina;
                //String login_url = "http://fiesta.mawetecnologias.com/api/proveedores?q=&cat=1&evt=1&pag=1";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("busquedaCategoria")) {
            try {
                String categoria = params[1];
                String login_url = "http://fiesta.mawetecnologias.com/api/proveedores" +
                        "?cat=" + categoria;
                //String login_url = "http://fiesta.mawetecnologias.com/api/proveedores?q=&cat=1&evt=1&pag=1";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("detallesCategoria")) {
            try {
                String id = params[1];
                String login_url = "http://fiesta.mawetecnologias.com/api/proveedores/" + id;
                //String login_url = "http://fiesta.mawetecnologias.com/api/proveedores?q=&cat=1&evt=1&pag=1";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("eventos")) {
            try {
                String login_url = "http://fiesta.mawetecnologias.com/api/tipos_evento";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        String token;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
        alertDialog.setMessage(result);

        if (type.equals("categorias")) {
            if (result != null) {
                try {
                    int id;
                    String categoria;
                    String caller = context.getClass().getSimpleName();

                    if(caller.equals("MainActivity")){
                        MainActivity mainActivity = (MainActivity) context;

                        JSONArray jsonArray = new JSONArray(result);

                        int arraySize = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            categoria = jsonObject.getString("descripcion");

                            if (!categoria.isEmpty()) {
                                mainActivity.fillExampleList(id, categoria, arraySize);

                            }
                        }
                    }else if(caller.equals("ProveedoresActivity")){
                        ProveedoresActivity proveedoresActivity = (ProveedoresActivity) context;

                        JSONArray jsonArray = new JSONArray(result);

                        int arraySize = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            categoria = jsonObject.getString("descripcion");

                            if (!categoria.isEmpty()) {
                                proveedoresActivity.fillCategoriaList(id, categoria, arraySize);

                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equals("busqueda")) {
            if (result != null) {
                try {
                    int id;
                    String nombre;
                    String foto;
                    int totalDePaginas;
                    ProveedoresActivity proveedoresActivity = (ProveedoresActivity) context;

                    JSONObject jsonObject = new JSONObject(result);
                    totalDePaginas = jsonObject.getInt("total_paginas");
                    JSONArray jsonArray = jsonObject.getJSONArray("resultados");
                    int arraySize = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject joResultados = jsonArray.getJSONObject(i);
                        id = joResultados.getInt("id");
                        nombre = joResultados.getString("nombre");
                        foto = joResultados.getString("foto");

                        if (!nombre.isEmpty()) {
                            proveedoresActivity.fillExampleList(id, nombre, foto, arraySize);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equals("busquedaCategoria")) {
            if (result != null) {
                try {
                    int id;
                    String nombre;
                    String foto;
                    int totalDePaginas;
                    CategoriaActivity categoriaActivity = (CategoriaActivity) context;

                    JSONObject jsonObject = new JSONObject(result);
                    totalDePaginas = jsonObject.getInt("total_paginas");
                    JSONArray jsonArray = jsonObject.getJSONArray("resultados");
                    int arraySize = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject joResultados = jsonArray.getJSONObject(i);
                        id = joResultados.getInt("id");
                        nombre = joResultados.getString("nombre");
                        foto = joResultados.getString("foto");

                        if (!nombre.isEmpty()) {
                            categoriaActivity.fillExampleList(id, nombre, foto, arraySize);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equals("detallesCategoria")) {
            if (result != null) {
                try {
                    int id;
                    String nombre;
                    String telefono;
                    String foto;
                    String facebook;
                    String sitioWeb;
                    String instagram;
                    CategoriaActivity categoriaActivity;
                    ProveedoresActivity proveedoresActivity;

                    String caller = context.getClass().getSimpleName();

                    if(caller.equals("CategoriaActivity")){
                        categoriaActivity = (CategoriaActivity) context;

                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject jsonDetalles = jsonObject.getJSONObject("0");
                        nombre = jsonDetalles.getString("nombre");
                        telefono = jsonDetalles.getString("telefono");
                        foto = jsonDetalles.getString("foto");
                        facebook = jsonDetalles.getString("facebook");
                        sitioWeb = jsonDetalles.getString("sitio_web");
                        instagram = jsonDetalles.getString("instagram");

                        String foto_url = "http://fiesta.mawetecnologias.com/img/uploads/" + foto;


                        if(nombre != null){
                            categoriaActivity.fillDetallesList(
                                    nombre,
                                    sitioWeb,
                                    foto_url,
                                    facebook,
                                    telefono,
                                    instagram);
                        }
                    }else if(caller.equals("ProveedoresActivity")){
                        proveedoresActivity = (ProveedoresActivity) context;

                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject jsonDetalles = jsonObject.getJSONObject("0");
                        nombre = jsonDetalles.getString("nombre");
                        telefono = jsonDetalles.getString("telefono");
                        foto = jsonDetalles.getString("foto");
                        facebook = jsonDetalles.getString("facebook");
                        sitioWeb = jsonDetalles.getString("sitio_web");
                        instagram = jsonDetalles.getString("instagram");

                        String foto_url = "http://fiesta.mawetecnologias.com/img/uploads/" + foto;


                        if(nombre != null){
                            proveedoresActivity.fillDetallesList(
                                    nombre,
                                    sitioWeb,
                                    foto_url,
                                    facebook,
                                    telefono,
                                    instagram);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equals("eventos")) {
            if (result != null) {
                try {
                    int id;
                    String categoria;
                    ProveedoresActivity proveedoresActivity = (ProveedoresActivity) context;

                    JSONArray jsonArray = new JSONArray(result);

                    int arraySize = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        categoria = jsonObject.getString("descripcion");

                        if (!categoria.isEmpty()) {
                            proveedoresActivity.fillEventosList(id, categoria, arraySize);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onProgressUpdate (Void...values){
        super.onProgressUpdate(values);
    }
}