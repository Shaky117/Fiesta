package com.tullipan.fiesta;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListProveedoresActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvProveedores;

    private Context context;

    private ListProveedorAdapter adapter;
    private ArrayList<ProveedoresItem> proveedoresList;

    private int id;

    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_proveedores);

        context = this;

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, android.R.color.black));
        }

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        proveedoresList = new ArrayList<>();

        BackgroundWorker backgroundWorker = new BackgroundWorker(context);
        String type = "busqueda";
        backgroundWorker.execute(type, "", "", "" , "");

        ImageView btnAtras = findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(this);
    }

    public void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvListProveedor);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ListProveedorAdapter(proveedoresList, id,new ListProveedorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProveedoresItem proveedoresItem) {
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void fillProveedoresList(int id, String nombre, int size) {

        proveedoresList.add(new ProveedoresItem(id, nombre));

        if(this.size < size - 1){
            this.size++;
        }else{
            setUpRecyclerView();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAtras:
                finish();
                break;
        }
    }
}
