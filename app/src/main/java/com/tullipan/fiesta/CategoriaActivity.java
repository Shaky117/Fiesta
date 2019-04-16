package com.tullipan.fiesta;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoriaActivity extends AppCompatActivity implements View.OnClickListener {

    private ProveedoresAdapter adapter;
    private List<ProveedoresItem> proveedoresList;
    private Bundle bundle;
    private int size;
    private CategoriaActivity context;
    private String nombreDetalles;
    private String sitioDetalles;
    private String fotoDetalles;
    private String facebookDetalles;
    private String telefonoDetalles;
    private String instagramDetalles;
    private Drawable imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        context = this;
        proveedoresList = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.fiestaBackground));
        }

        ImageView btnHome = findViewById(R.id.btnHome);
        ImageView btnSearch = findViewById(R.id.btnSearchCategoria);

        //searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        bundle = getIntent().getExtras();

        int categoria = bundle.getInt("categoria", 0);
        String categoriaString = String.valueOf(categoria);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        String type = "busquedaCategoria";

        backgroundWorker.execute(type, categoriaString);

        try {
            backgroundWorker.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btnSearch.setOnClickListener(this);
        btnHome.setOnClickListener(this);
    }

    public void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvSearch);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ProveedoresAdapter(proveedoresList, new ProveedoresAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProveedoresItem proveedoresItem) {
                String id = String.valueOf(proveedoresItem.getId());
                BackgroundWorker detalles = new BackgroundWorker(context);
                String type = "detallesCategoria";
                detalles.execute(type, id);

                try {
                    detalles.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void createBottomSheet(){

        BottomSheet bottomSheet = new BottomSheet.Builder(context)
                .title(this.nombreDetalles)
                //.icon()
                .sheet(R.menu.list)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.telefonoDetalles:
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:" + telefonoDetalles));
                                startActivity(callIntent);
                                break;
                        }
                    }
                }).show();
        Menu menu = bottomSheet.getMenu();
        MenuItem telefono = menu.findItem(R.id.telefonoDetalles);
        MenuItem facebook = menu.findItem(R.id.facebookDetalles);
        MenuItem sitioWeb = menu.findItem(R.id.sitioWebContent);

        telefono.setTitle(this.telefonoDetalles);
        facebook.setTitle(this.facebookDetalles);
        sitioWeb.setTitle(this.sitioDetalles);
    }

    public void fillDetallesList(String nombre, String sitio, String foto, String facebbok, String telefono, String instagram){
        if(nombre.equals("null")){
            nombre = "";
        }

        if(sitio.equals("null")){
            sitio = "";
        }

        if(foto.equals("null")){
            foto = "";
        }

        if(facebbok.equals("null")){
            facebbok = "";
        }

        if(telefono.equals("null")){
            telefono = "";
        }

        if(instagram.equals("null")){
            instagram = "";
        }

        this.nombreDetalles = nombre;
        this.sitioDetalles = sitio;
        this.fotoDetalles = foto;
        this.facebookDetalles = facebbok;
        this.telefonoDetalles = telefono;
        this.instagramDetalles = instagram;

        createBottomSheet();
    }

    public void fillExampleList(int id, String nombre, String foto, int size) {
        proveedoresList.add(new ProveedoresItem(id, nombre, foto));

        if(this.size < size - 1){
            this.size++;
        }else{
            setUpRecyclerView();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnHome:
                startActivity(new Intent(CategoriaActivity.this, MainActivity.class));
                finishAffinity();
                break;
            case R.id.btnSearchCategoria:
                int categoria = bundle.getInt("categoria", 0);
                String categoriaString = String.valueOf(categoria);
                Intent intent = new Intent(CategoriaActivity.this, ProveedoresActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("categoria", categoriaString);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
