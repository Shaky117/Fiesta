package com.tullipan.fiesta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProveedoresActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ProveedoresAdapter adapter;
    private ArrayList<ProveedoresItem> proveedoresList;
    private ArrayList<String> categorias;
    private ArrayList<String> eventos;
    private  BackgroundWorker backgroundWorker;
    private int size;
    private int sizeCategoria;
    private ProveedoresActivity proveedoresActivity;
    private Context context;
    private String nombreDetalles;
    private String sitioDetalles;
    private String fotoDetalles;
    private String facebookDetalles;
    private String telefonoDetalles;
    private String instagramDetalles;
    private int sizeEventos;
    private int categoriaBusqueda;
    private int eventoBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedores);
        context = this;
        proveedoresActivity = this;
        final SearchView searchView = findViewById(R.id.searchProveedores);
        backgroundWorker = new BackgroundWorker(context);
        proveedoresList = new ArrayList<>();
        categorias = new ArrayList<>();
        eventos = new ArrayList<>();

        BackgroundWorker categoriaWorker = new BackgroundWorker(context);
        BackgroundWorker eventoWorker = new BackgroundWorker(context);

        String categoria = "categorias";
        categoriaWorker.execute(categoria);
        String eventos = "eventos";
        eventoWorker.execute(eventos);

        try {
            categoriaWorker.get();
            eventoWorker.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.fiestaBackground));
        }

        Bundle bundle = getIntent().getExtras();

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        int closeButtonId = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.btn_search);
        v.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = 125;
        params.width = 125;
        v.setLayoutParams(params);
        ImageView closeButtonImage = searchView.findViewById(closeButtonId);
        closeButtonImage.setColorFilter(Color.WHITE);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.WHITE);

        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        ImageView btnHome = findViewById(R.id.btnHome);

        //searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                backgroundWorker = new BackgroundWorker(context);
                proveedoresList = new ArrayList<>();

                String type = "busqueda";
                String catBusqueda = String.valueOf(categoriaBusqueda);
                String eventBusqueda = String.valueOf(eventoBusqueda);
                String busqueda = newText;
                String pagina = "1";
                backgroundWorker.execute(type, busqueda, catBusqueda, eventBusqueda, pagina);
                try {
                    backgroundWorker.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        closeButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    public void fillEventosList(int id, String nombre, int size){

        eventos.add(nombre);
        if(this.sizeCategoria < size - 1){
            this.sizeEventos++;
        }else{
            setUpSpinner();
        }
    }

    public void fillCategoriaList(int id, String nombre, int size){

        categorias.add(nombre);
        if(this.sizeCategoria < size - 1){
            this.sizeCategoria++;
        }
    }

    private void setUpSpinner() {
        Spinner spinnerCategorias = findViewById(R.id.spinnerCat);
        Spinner spinnerEventos = findViewById(R.id.spinnerEventos);
        ArrayAdapter<String> adapterCat = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, categorias);
        ArrayAdapter<String> adapterEvent = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, eventos);
        spinnerCategorias.setAdapter(adapterCat);
        spinnerEventos.setAdapter(adapterEvent);

        spinnerCategorias.setOnItemSelectedListener(this);
        spinnerEventos.setOnItemSelectedListener(this);

    }

    public void fillExampleList(int id, String nombre, String foto,int size) {

        proveedoresList.add(new ProveedoresItem(id, nombre, foto));

        if(this.size < size - 1){
            this.size++;
        }else{
            setUpRecyclerView();
        }
    }

    public void createBottomSheet(){

        BottomSheet bottomSheet = new BottomSheet.Builder(proveedoresActivity)
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnHome:
                startActivity(new Intent(ProveedoresActivity.this, MainActivity.class));
                finishAffinity();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinnerCat:
                this.categoriaBusqueda = position + 1;
                break;
            case R.id.spinnerEventos:
                this.eventoBusqueda = position + 1;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
