package com.tullipan.fiesta;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NuevoEventoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String CERO = "0";
    private static final String BARRA = "-";

    public final Calendar c = Calendar.getInstance();


    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    private ArrayList<EventoProveedor> eventoProveedorsList;

    private Context context;

    private ArrayList<String> eventos;
    private ArrayList<String> ids;

    private EditText etNombre;
    private EditText etFecha;
    private EditText etTipodeEvento;

    private RecyclerView rvProveedores;
    private EventoProveedoresAdapter eventoProveedoresAdapter;

    private int sizeEventos;
    private String eventoBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);

        context = this;

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.fiestaBackground));
        }

        eventoProveedorsList = new ArrayList<>();
        eventos = new ArrayList<>();
        ids = new ArrayList<>();

        BackgroundWorker eventoWorker = new BackgroundWorker(context);
        String eventos = "eventos";
        eventoWorker.execute(eventos);

        rvProveedores = findViewById(R.id.rvProveedor);

        etNombre = findViewById(R.id.etNombre);
        etFecha = findViewById(R.id.etFecha);

        Button btnAgregar = findViewById(R.id.btnAgregarProveedor);
        Button btnEditar = findViewById(R.id.btnEditarDetalles);
        ImageView btnAtras = findViewById(R.id.btnAtras);
        ImageView btnDatePicker = findViewById(R.id.btnPicker);

        btnAgregar.setOnClickListener(this);
        btnAtras.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);

        BackgroundWorker backgroundWorker = new BackgroundWorker(context);
        String type = "eventoProveedor";
        backgroundWorker.execute(type);
    }

    public void isCompleted(boolean completed){
        if(completed){
            Toast.makeText(getApplicationContext(), "Se a guardado el evento", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "A ocurrdio un error", Toast.LENGTH_SHORT).show();
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

    private void setUpRvProveedor(){
        rvProveedores.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        eventoProveedoresAdapter = new EventoProveedoresAdapter(eventoProveedorsList, 0,new EventoProveedoresAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EventoProveedor eventos) {

            }
        });
        rvProveedores.setLayoutManager(layoutManager);
        rvProveedores.setAdapter(eventoProveedoresAdapter);
    }

    private void setUpSpinner() {
        Spinner spinnerEventos = findViewById(R.id.spinnerEventos);
        ArrayAdapter<String> adapterEvent = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, eventos);
        spinnerEventos.setAdapter(adapterEvent);

        spinnerEventos.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregarProveedor:
                Intent agregar = new Intent(NuevoEventoActivity.this, ListProveedoresActivity.class);
                startActivity(agregar);
                break;
            case R.id.btnAtras:
                finish();
                break;
            case R.id.btnEditarDetalles:
                String nombre = etNombre.getText().toString();
                String fecha = etFecha.getText().toString();
                String tipoEvento = eventoBusqueda;
                BackgroundWorker backgroundWorker = new BackgroundWorker(context);
                String type = "guardarEvento";
                backgroundWorker.execute(type, nombre, fecha, tipoEvento);
                break;
            case R.id.btnPicker:
                obtenerFecha();
                break;
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

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
