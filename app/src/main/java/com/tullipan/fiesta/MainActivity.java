package com.tullipan.fiesta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CategoriasAdapter adapter;
    private ArrayList<Categorias> categoriasList;
    private Bundle bundle;
    private int size;
    private boolean finished = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriasList = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.fiestaBackground));
        }

        ImageView btnSearch = findViewById(R.id.btnSearch);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        String type = "categorias";
        backgroundWorker.execute(type);

        try {
            backgroundWorker.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(finished){
            setUpRecyclerView();
        }

        btnSearch.setOnClickListener(this);
    }

    public void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvCategorias);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new CategoriasAdapter(categoriasList, new CategoriasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Categorias categorias) {
                Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("categoria", categorias.getId());
                bundle.putString("categoriaNombre", categorias.getCategoria());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void fillExampleList(int id, String nombre, String imagen,int size) {

        String foto = "";

        categoriasList.add(new Categorias(id, nombre, imagen));

        if(this.size < size - 1){
            this.size++;
        }else{
            finished = true;
            setUpRecyclerView();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSearch:
                startActivity(new Intent(MainActivity.this, ProveedoresActivity.class));
                break;
        }
    }
}