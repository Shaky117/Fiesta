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
import android.widget.Toast;

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
    int responseCode;

    String url= "http://admin.easyparty.mx";

    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];

        if (type.equals("login")) {
            try {
                String login_url = url + "/oauth/token";
                String email = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("grant_type", "UTF-8")+"="+URLEncoder.encode("password", "UTF-8")+"&"
                        +URLEncoder.encode("client_id", "UTF-8")+"="+URLEncoder.encode("3", "UTF-8")+"&"
                        +URLEncoder.encode("client_secret", "UTF-8")+"="+URLEncoder.encode("9qoCkRQ57DQTqCTu6SCjMhR8IA0zq9HbO3f9CzDv", "UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("scope","UTF-8")+"="+URLEncoder.encode("","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                responseCode = httpURLConnection.getResponseCode();
                InputStream inputStream = null;
                if(responseCode >= 200 && responseCode < 400) {
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        }else if(type.equals("register")) {
            try {
                String login_url = url + "/api/registro";
                String nombre = params[1];
                String correo = params[2];
                String password = params[3];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Accept", "application/json");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("correo", "UTF-8")+"="+URLEncoder.encode(correo, "UTF-8")+"&"
                                +URLEncoder.encode("nombre", "UTF-8")+"="+URLEncoder.encode(nombre, "UTF-8")+"&"
                                +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        } else if (type.equals("categorias")) {
            try {
                String login_url = url +"/api/categorias";
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
                String login_url = url + "/api/proveedores" +
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
                String login_url = url + "/api/proveedores" +
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
                String login_url = url + "/api/proveedores/" + id;
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
                String login_url = url + "/api/tipos_evento";
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
        }else if (type.equals("noticias")) {
            try {
                String login_url = url + "/api/noticias";
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
        }else if(type.equals("user")){
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
            try {
                String login_url = url + "/api/user/";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + sharedPreferences.getString("token", null));
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        }else if(type.equals("eventoUser")){
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
            try {
                String login_url = url + "/api/eventos";
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + sharedPreferences.getString("token", null));
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        }else if(type.equals("editarEvento")){
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
            try {
                String id = params[1];
                String login_url = url + "/api/eventos/" + id;
                String nombre = params[2];
                String fecha = params[3];
                String tipoEvento = params[4];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("PUT");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + sharedPreferences.getString("token", null));
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("nombre", "UTF-8")+"="+URLEncoder.encode(nombre, "UTF-8")+"&"
                                +URLEncoder.encode("tipo_evento", "UTF-8")+"="+URLEncoder.encode(tipoEvento, "UTF-8")+"&"
                                +URLEncoder.encode("fecha", "UTF-8")+"="+URLEncoder.encode(fecha, "UTF-8")+"&"
                                +URLEncoder.encode("invitados", "UTF-8")+"="+URLEncoder.encode("2000", "UTF-8")+"&"
                                +URLEncoder.encode("presupuesto", "UTF-8")+"="+URLEncoder.encode("2000", "UTF-8")+"&"
                                +URLEncoder.encode("foto", "UTF-8")+"="+URLEncoder.encode("", "UTF-8")+"&"
                                +URLEncoder.encode("nombre_archivo","UTF-8")+"="+URLEncoder.encode("","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                responseCode = httpURLConnection.getResponseCode();
                InputStream inputStream = null;
                if(responseCode >= 200 && responseCode < 400) {
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        }else if(type.equals("detallesEventos")){
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
            try {
                String id = params[1];
                String login_url = url + "/api/eventos/" + id;
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + sharedPreferences.getString("token", null));
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        }else if(type.equals("guardarEvento")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
            try {
                String login_url = url + "/api/eventos";
                String nombre = params[1];
                String fecha = params[2];
                String tipoEvento = params[3];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + sharedPreferences.getString("token", null));
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("nombre", "UTF-8")+"="+URLEncoder.encode(nombre, "UTF-8")+"&"
                                +URLEncoder.encode("tipo_evento", "UTF-8")+"="+URLEncoder.encode(tipoEvento, "UTF-8")+"&"
                                +URLEncoder.encode("fecha", "UTF-8")+"="+URLEncoder.encode(fecha, "UTF-8")+"&"
                                +URLEncoder.encode("invitados", "UTF-8")+"="+URLEncoder.encode("100", "UTF-8")+"&"
                                +URLEncoder.encode("presupuesto","UTF-8")+"="+URLEncoder.encode("2000","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        }else if(type.equals("eliminarEvento")){
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
            try {
                String id = params[1];
                String login_url = url + "/api/eventos/" + id;
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("DELETE");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + sharedPreferences.getString("token", null));
                responseCode = httpURLConnection.getResponseCode();
                InputStream inputStream = null;
                if(responseCode >= 200 && responseCode < 400) {
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        } else if(type.equals("addProveedor")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
            try {
                String id = params[1];
                String login_url = url + "/api/eventos/" + id + "/proveedor";
                String idProveedor = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + sharedPreferences.getString("token", null));
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("proveedor", "UTF-8")+"="+URLEncoder.encode(idProveedor, "UTF-8")+"&"
                                +URLEncoder.encode("invitados", "UTF-8")+"="+URLEncoder.encode("100", "UTF-8")+"&"
                                +URLEncoder.encode("presupuesto","UTF-8")+"="+URLEncoder.encode("2000","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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
        } else if(type.equals("eliminarProveedor")){
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
            try {
                String id = params[1];
                String idProveedor = params[2];
                String login_url = url + "/api/eventos/" + id + "/proveedor/" + idProveedor;
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("DELETE");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Authorization", "Bearer " + sharedPreferences.getString("token", null));
                responseCode = httpURLConnection.getResponseCode();
                InputStream inputStream = null;
                if(responseCode >= 200 && responseCode < 400) {
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
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

        if(type.equals("login")) {
            try {
                if(responseCode >= 200 && responseCode < 400) {
                    LoginAcitivity loginAcitivity = (LoginAcitivity) context;
                    JSONObject jObj = new JSONObject(result);
                    token = jObj.getString("access_token");
                    Boolean complete;
                    if (token != null) {
                        complete = true;
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.commit();

                        loginAcitivity.completedLogin(complete);
                    }
                }else{
                    LoginAcitivity loginActivity = (LoginAcitivity) context;
                    loginActivity.completedLogin(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else  if(type.equals("register")){
            try {
                if(context instanceof RegisterActivity){
                    RegisterActivity registerActivity = (RegisterActivity) context;

                    JSONObject registerObject = new JSONObject(result);

                    boolean completed = registerObject.getBoolean("exito");


                    if(completed){
                        registerActivity.registerCompleted(completed, "");
                    }else{
                        String mensaje = registerObject.getString("mensaje");
                        registerActivity.registerCompleted(completed, mensaje);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals("categorias")) {
            if (result != null) {
                try {
                    int id;
                    String categoria;
                    String imagen;
                    String caller = context.getClass().getSimpleName();

                    if(caller.equals("MainScreen")){
                        MainScreen mainScreen = (MainScreen) context;

                        JSONArray jsonArray = new JSONArray(result);

                        int arraySize = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            categoria = jsonObject.getString("descripcion");
                            imagen = jsonObject.getString("imagen");

                            if (!categoria.isEmpty()) {
                                mainScreen.fillExampleList(id, categoria, imagen, arraySize);

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
                if(context instanceof ProveedoresActivity) {
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
                }else if(context instanceof ListProveedoresActivity) {
                    try {
                        int id;
                        String nombre;
                        String foto;
                        int totalDePaginas;
                        ListProveedoresActivity listProveedoresActivity = (ListProveedoresActivity) context;

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
                                listProveedoresActivity.fillProveedoresList(id, nombre, arraySize);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (type.equals("busquedaCategoria")) {
            if (result != null) {
                if(context instanceof CategoriaActivity) {
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
                }else if(context instanceof ListProveedoresActivity) {
                    try {
                        int id;
                        String nombre;
                        String foto;
                        int totalDePaginas;
                        ListProveedoresActivity listProveedoresActivity = (ListProveedoresActivity) context;

                        JSONObject jsonObject = new JSONObject(result);
                        totalDePaginas = jsonObject.getInt("total_paginas");
                        JSONArray jsonArray = jsonObject.getJSONArray("resultados");
                        int arraySize = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject joResultados = jsonArray.getJSONObject(i);
                            id = joResultados.getInt("id");
                            nombre = joResultados.getString("nombre");

                            if (!nombre.isEmpty()) {
                                listProveedoresActivity.fillProveedoresList(id, nombre, arraySize);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    DetallesActivity detallesActivity;

                    String caller = context.getClass().getSimpleName();

                    detallesActivity = (DetallesActivity) context;

                    JSONObject jsonObject = new JSONObject(result);
                    nombre = jsonObject.getString("nombre");
                    telefono = jsonObject.getString("telefono");
                    foto = jsonObject.getString("foto");
                    facebook = jsonObject.getString("facebook");
                    sitioWeb = jsonObject.getString("pinterest");
                    instagram = jsonObject.getString("instagram");

                    String foto_url = "http://fiesta.mawetecnologias.com/img/uploads/" + foto;


                    if(nombre != null){
                        detallesActivity.fillDetallesList(
                                nombre,
                                sitioWeb,
                                foto_url,
                                facebook,
                                telefono,
                                instagram);
                    }

                    JSONArray jsonArray = jsonObject.getJSONArray("imagenes");
                    int size = jsonArray.length();

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        int idImg = jsonObject1.getInt("id");
                        String url = jsonObject1.getString("url");

                        if(!url.isEmpty()){
                            detallesActivity.fillImagenesList(idImg, url, size);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equals("eventos")) {
            if (result != null) {
                if(context instanceof ProveedoresActivity) {
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
                }else if(context instanceof EventoActivity) {
                    try {
                        int id;
                        String categoria;
                        EventoActivity eventoActivity = (EventoActivity) context;

                        JSONArray jsonArray = new JSONArray(result);

                        int arraySize = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            categoria = jsonObject.getString("descripcion");

                            if (!categoria.isEmpty()) {
                                eventoActivity.fillEventosList(id, categoria, arraySize);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(context instanceof NuevoEventoActivity) {
                    try {
                        int id;
                        String categoria;
                        NuevoEventoActivity eventoActivity = (NuevoEventoActivity) context;

                        JSONArray jsonArray = new JSONArray(result);

                        int arraySize = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            categoria = jsonObject.getString("descripcion");

                            if (!categoria.isEmpty()) {
                                eventoActivity.fillEventosList(id, categoria, arraySize);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if (type.equals("noticias")) {
            if (result != null) {
                try {
                    int id;
                    int idProveedor;
                    String titulo;
                    String descripcion;
                    String imagen;
                    MainScreen mainScreen = (MainScreen) context;

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("noticias");

                    int arraySize = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject noticia = jsonArray.getJSONObject(i);
                        id = noticia.getInt("id");
                        titulo = noticia.getString("titulo");
                        descripcion = noticia.getString("descripcion");
                        imagen = noticia.getString("imagen");
                        idProveedor = noticia.getInt("id_proveedor");

                        if (!titulo.isEmpty()) {
                            mainScreen.fillNoticiasList(id, imagen, titulo, descripcion, idProveedor, arraySize);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else  if(type.equals("user")){
            try {
                if(context instanceof MainScreen){
                    MainScreen mainScreen = (MainScreen) context;

                    JSONObject jsonObject = new JSONObject(result);

                    String nombre = jsonObject.getString("name");
                    String foto = jsonObject.getString("foto");
                    String email = jsonObject.getString("email");

                    mainScreen.setUpUser(nombre, foto, email);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else  if(type.equals("editUser")){
            try {
                if(context instanceof EditActivity){
                    EditActivity userActivity = (EditActivity) context;

                    JSONObject jsonObject = new JSONObject(result);

                    boolean completed = jsonObject.getBoolean("exito");

                    userActivity.editCompleted(completed, "");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals("eventoUser")) {
            if (result != null) {
                try {
                    int id;
                    String nombre;
                    String fecha;
                    MainScreen mainScreen = (MainScreen) context;

                    JSONArray jsonArray = new JSONArray(result);

                    int arraySize = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        nombre = jsonObject.getString("nombre");
                        fecha = jsonObject.getString("fecha");

                        if (!nombre.isEmpty()) {
                            mainScreen.fillEventosList(id, nombre, fecha, arraySize);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equals("guardarEvento")) {
            if (result != null) {
                if(context instanceof NuevoEventoActivity) {
                    try {
                        boolean completed;
                        NuevoEventoActivity nuevoEventoActivity = (NuevoEventoActivity) context;

                        JSONObject jsonObject = new JSONObject(result);
                        completed = jsonObject.getBoolean("exito");

                        nuevoEventoActivity.isCompleted(completed);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if (type.equals("detallesEventos")) {
            if (result != null) {
                if(context instanceof EventoActivity) {
                    try {
                        int id;
                        String nombre;
                        String fecha;
                        int tipoEvento;

                        EventoActivity eventoActivity = (EventoActivity) context;

                        JSONObject jsonObject = new JSONObject(result);

                        id = jsonObject.getInt("id");
                        nombre = jsonObject.getString("nombre");
                        fecha = jsonObject.getString("fecha");
                        tipoEvento = jsonObject.getInt("id_tipo_evento");

                        eventoActivity.setUpViews(nombre, fecha, tipoEvento);

                        JSONArray jsonArray = jsonObject.getJSONArray("proveedores");
                        int arraySize = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject proveedor = jsonArray.getJSONObject(i);
                            id = proveedor.getInt("id");
                            nombre = proveedor.getString("nombre");

                            if (!nombre.isEmpty()) {
                                eventoActivity.fillProovedorList(id, nombre, arraySize);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if (type.equals("eliminarEvento")) {
            if (result != null) {
                if(context instanceof EventoActivity) {
                    try {
                        boolean completed;
                        EventoActivity eventoActivity = (EventoActivity) context;

                        JSONObject jsonObject = new JSONObject(result);
                        completed = jsonObject.getBoolean("exito");

                        eventoActivity.isCompleted(completed);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if (type.equals("addProveedor")) {
            if (result != null) {
                    try {
                        boolean completed;
                        ListProveedoresActivity listProveedoresActivity = (ListProveedoresActivity) context;

                        JSONObject jsonObject = new JSONObject(result);
                        completed = jsonObject.getBoolean("exito");

                        if(completed){
                            Toast.makeText(context, "Se ha agregado al proveedor", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "A ocurrido un error", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }else if (type.equals("eliminarProveedor")) {
            if (result != null) {
                if(context instanceof EventoActivity) {
                    try {
                        boolean completed;


                        EventoActivity adapter = (EventoActivity) context;

                        JSONObject jsonObject = new JSONObject(result);
                        completed = jsonObject.getBoolean("exito");

                        adapter.refreshRV(completed);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else if (type.equals("editarEvento")) {
            if (result != null) {
                if(context instanceof EventoActivity) {
                    try {
                        boolean completed;


                        EventoActivity adapter = (EventoActivity) context;

                        JSONObject jsonObject = new JSONObject(result);
                        completed = jsonObject.getBoolean("exito");

                        adapter.refreshRV(completed);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @Override
    protected void onProgressUpdate (Void...values){
        super.onProgressUpdate(values);
    }
}