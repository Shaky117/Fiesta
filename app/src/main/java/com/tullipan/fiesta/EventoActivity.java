package com.tullipan.fiesta;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EventoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ArrayList<EventoProveedor> eventoProveedorsList;

    private Context context;

    private EditText etNombre;
    private EditText etFecha;
    private EditText etTipodeEvento;

    private ArrayList<String> eventos;
    private ArrayList<String> ids;

    private RecyclerView rvProveedores;
    private EventoProveedoresAdapter eventoProveedoresAdapter;
    private Spinner spinnerEventos;

    private int sizeEventos;
    private String eventoBusqueda;
    private int sizeProveedor;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        context = this;

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.fiestaBackground));
        }

        Bundle bundle = getIntent().getExtras();

        id = bundle.getInt("id");

        eventoProveedorsList = new ArrayList<>();
        eventos = new ArrayList<>();
        ids = new ArrayList<>();

        rvProveedores = findViewById(R.id.rvProveedor);

        etNombre = findViewById(R.id.etNombre);
        etFecha = findViewById(R.id.etFecha);
        spinnerEventos = findViewById(R.id.spinnerEventos);

        Button btnAgregar = findViewById(R.id.btnAgregarProveedor);
        Button btnEditar = findViewById(R.id.btnEditarDetalles);
        ImageView btnElimiar = findViewById(R.id.btnEliminar);
        ImageView btnAtras = findViewById(R.id.btnAtras);

        btnAgregar.setOnClickListener(this);
        btnAtras.setOnClickListener(this);
        btnElimiar.setOnClickListener(this);
        btnEditar.setOnClickListener(this);

        BackgroundWorker eventoWorker = new BackgroundWorker(context);
        String eventos = "eventos";
        eventoWorker.execute(eventos);

        BackgroundWorker detallesEvento = new BackgroundWorker(context);
        String detallesEventos = "detallesEventos";
        detallesEvento.execute(detallesEventos, String.valueOf(id));
    }

    public void setUpViews(String nombre, String fecha, int tipoEvento){
        etNombre.setText(nombre);
        etFecha.setText(fecha);

        int arraySize = eventos.size();

        for (int i=0; i < ids.size(); i++){
            String id = ids.get(i);
            String tipo = String.valueOf(tipoEvento);
            if(id.equals(tipo)){
                spinnerEventos.setSelection(i);
            }
        }
    }

    public void fillProovedorList(int id, String nombre, int size){

        eventoProveedorsList.add(new EventoProveedor(id, nombre));
        if(this.sizeProveedor < size - 1){
            this.sizeProveedor++;
        }else{
            setUpRvProveedor();
        }
    }

    public void fillEventosList(int id, String nombre, int size){

        ids.add(String.valueOf(id));
        eventos.add(nombre);
        if(this.sizeEventos < size - 1){
            this.sizeEventos++;
        }else{
            setUpSpinner();
        }
    }

    public void refreshRV(boolean completed){
        if (completed) {
            Toast.makeText(getApplicationContext(), "Se ha actualizado con exito", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(context, "A ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        eventoProveedorsList.clear();
        BackgroundWorker detallesEvento = new BackgroundWorker(context);
        String detallesEventos = "detallesEventos";
        detallesEvento.execute(detallesEventos, String.valueOf(id));
        super.onResume();
    }

    private void setUpRvProveedor(){
        rvProveedores.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        eventoProveedoresAdapter = new EventoProveedoresAdapter(eventoProveedorsList, id, new EventoProveedoresAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EventoProveedor eventos) {

            }
        });
        rvProveedores.setLayoutManager(layoutManager);
        rvProveedores.setAdapter(eventoProveedoresAdapter);
    }

    private void setUpSpinner() {
        spinnerEventos = findViewById(R.id.spinnerEventos);
        ArrayAdapter<String> adapterEvent = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, eventos);
        spinnerEventos.setAdapter(adapterEvent);

        spinnerEventos.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregarProveedor:
                Intent agregar = new Intent(EventoActivity.this, ListProveedoresActivity.class);
                agregar.putExtra("id", id);
                startActivity(agregar);
                break;
            case R.id.btnEliminar:
                BackgroundWorker eliminar = new BackgroundWorker(context);
                String type = "eliminarEvento";
                eliminar.execute(type, String.valueOf(id));
                break;
            case R.id.btnAtras:
                finish();
                break;
            case R.id.btnEditarDetalles:
                String nombre = etNombre.getText().toString();
                String fecha = etFecha.getText().toString();

                BackgroundWorker editar = new BackgroundWorker(context);
                String e = "editarEvento";
                editar.execute(e, String.valueOf(id), nombre, fecha, eventoBusqueda);
                break;
        }
    }

    public void isCompleted(boolean completed){
        if(completed){
            Toast.makeText(getApplicationContext(), "Se ha eliminado el evento", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinnerEventos:
                this.eventoBusqueda = ids.get(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
