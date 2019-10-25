package com.tullipan.fiesta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    private NoticiasAdapter noticiasAdapter;
    private CategoriasAdapter categoriasAdapter;
    private EventosAdapter eventosAdapter;

    private ArrayList<NoticiasItem> noticiasItems;
    private ArrayList<Categorias> categoriasItems;
    private ArrayList<Eventos> eventosItems;

    private Context context;

    private CircleImageView userImage;
    private TextView tvNombre;
    private TextView tvCorreo;

    private int size;

    private RecyclerView rvNoticias;
    private RecyclerView rvCategorias;
    private RecyclerView rvEventos;

    private ViewFlipper viewFlipper;

    private Window window;

    private String url = "http://admin.easyparty.mx";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_noticias:
                    noticiasItems.clear();
                    BackgroundWorker noticias = new BackgroundWorker(context);
                    String noticia = "noticias";
                    noticias.execute(noticia);
                    viewFlipper.setDisplayedChild(0);
                    return true;
                case R.id.navigation_categorias:
                    categoriasItems.clear();
                    BackgroundWorker categorias = new BackgroundWorker(context);
                    String categoria = "categorias";
                    categorias.execute(categoria);
                    viewFlipper.setDisplayedChild(1);
                    return true;
                case R.id.navigation_usuarios:
                    eventosItems.clear();
                    BackgroundWorker eventos = new BackgroundWorker(context);
                    String evento = "eventoUser";
                    eventos.execute(evento);
                    viewFlipper.setDisplayedChild(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        viewFlipper = findViewById(R.id.viewFlipper);

        viewFlipper.setDisplayedChild(0);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        context = this;

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.fiestaBackground));
        }


        noticiasItems = new ArrayList<>();
        categoriasItems = new ArrayList<>();
        eventosItems = new ArrayList<>();

        View noticiasLayout = viewFlipper.findViewById(R.id.layoutNoticias);
        View categoriasLayout = viewFlipper.findViewById(R.id.layoutCategoria);
        View usuarioLayout = viewFlipper.findViewById(R.id.layoutUsuario);

        rvCategorias = categoriasLayout.findViewById(R.id.rvCategorias);
        ImageView btnSearch = categoriasLayout.findViewById(R.id.btnSearch);

        rvNoticias = noticiasLayout.findViewById(R.id.rvNoticias);
        rvEventos = usuarioLayout.findViewById(R.id.rvEventos);

        ImageView btnEdit = usuarioLayout.findViewById(R.id.btnEdit);
        ImageView btnLogout = usuarioLayout.findViewById(R.id.btnLogout);
        Button btnNewEvent = usuarioLayout.findViewById(R.id.btnNewEvent);
        userImage = usuarioLayout.findViewById(R.id.ivUser);
        tvNombre = usuarioLayout.findViewById(R.id.tvNombre);
        tvCorreo = usuarioLayout.findViewById(R.id.tvCorreo);

        BackgroundWorker noticias = new BackgroundWorker(context);
        String type = "noticias";
        noticias.execute(type);

        BackgroundWorker user = new BackgroundWorker(context);
        String userType = "user";
        user.execute(userType);

        btnEdit.setOnClickListener(this);
        btnNewEvent.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    public void setUpUser(String nombre, String imagen, String corre){
        if(!imagen.equals("") && imagen != null && !imagen.equals("null")){
            String path = url + "/storage/" + imagen ;
            Picasso.with(context).load(path).into(userImage);
        }else{
            userImage.setImageResource(R.mipmap.ic_fiesta_launcher);
        }
        tvNombre.setText(nombre);
        tvCorreo.setText(corre);
    }

    public void fillEventosList(int id, String nombre, String fecha, int size){
        eventosItems.add(new Eventos(id, nombre, fecha));

        if(this.size < size - 1){
            this.size++;
        }else{
            setUpRvEventos();
        }
    }

    public void fillNoticiasList(int id, String imagen, String noticia, String descripcion,int proveedor,int size){
        noticiasItems.add(new NoticiasItem(id, imagen, noticia, descripcion, proveedor));

        if(this.size < size - 1){
            this.size++;
        }else{
            setUpRvNoticias();
        }
    }

    public void fillExampleList(int id, String nombre, String imagen,int size) {

        String foto = "";

        categoriasItems.add(new Categorias(id, nombre, imagen));

        if(this.size < size - 1){
            this.size++;
        }else{
            setUpRvCategorias();
        }
    }

    private void setUpRvEventos(){
        rvEventos.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        eventosAdapter = new EventosAdapter(eventosItems, new EventosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Eventos eventos) {
                Intent editar = new Intent(MainScreen.this, EventoActivity.class);
                editar.putExtra("id", eventos.getId());
                startActivity(editar);
            }
        });
        rvEventos.setLayoutManager(layoutManager);
        rvEventos.setAdapter(eventosAdapter);
    }

    private void setUpRvNoticias(){
        rvNoticias.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        noticiasAdapter = new NoticiasAdapter(noticiasItems, new NoticiasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoticiasItem noticiasItem) {
                String id = String.valueOf(noticiasItem.getIdProveedor());
                String imagen = noticiasItem.getImagen();
                String titulo = noticiasItem.getNoticia();
                String descripcion = noticiasItem.getDescripcion();

                Intent detallesActivity = new Intent(MainScreen.this, NoticiaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("imagen", imagen);
                bundle.putString("titulo", titulo);
                bundle.putString("descripcion", descripcion);
                detallesActivity.putExtras(bundle);
                startActivity(detallesActivity);
            }
        });
        rvNoticias.setLayoutManager(layoutManager);
        rvNoticias.setAdapter(noticiasAdapter);
    }

    private void setUpRvCategorias(){
        rvCategorias.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        categoriasAdapter = new CategoriasAdapter(categoriasItems, new CategoriasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Categorias categorias) {
                Intent intent = new Intent(MainScreen.this, CategoriaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("categoria", categorias.getId());
                bundle.putString("categoriaNombre", categorias.getCategoria());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvCategorias.setLayoutManager(layoutManager);
        rvCategorias.setAdapter(categoriasAdapter);
    }

    @Override
    protected void onResume() {
        BackgroundWorker user = new BackgroundWorker(context);
        String userType = "user";
        user.execute(userType);

        eventosItems.clear();
        BackgroundWorker eventos = new BackgroundWorker(context);
        String evento = "eventoUser";
        eventos.execute(evento);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnEdit:
                Intent edit = new Intent(MainScreen.this, EditActivity.class);
                startActivity(edit);
                break;
            case R.id.btnNewEvent:
                Intent newEvent = new Intent(MainScreen.this, NuevoEventoActivity.class);
                startActivity(newEvent);
                break;
            case R.id.btnSearch:
                startActivity(new Intent(MainScreen.this, ProveedoresActivity.class));
                break;
            case R.id.btnLogout:
                SharedPreferences sharedPreferences = getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                finish();
                startActivity(new Intent(MainScreen.this, LoginAcitivity.class));
                break;
        }
    }
}
