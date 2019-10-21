package com.tullipan.fiesta;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.opensooq.pluto.PlutoIndicator;
import com.opensooq.pluto.PlutoView;

import java.util.ArrayList;


public class DetallesActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Imagenes> imagenes;
    private int size;

    private TextView txtNombre;
    private TextView txtSitio;
    private TextView txtFacebook;
    private TextView txtTelefono;
    private TextView txtInstagram;

    private String llamada;
    private String sitioWeb;
    private String sitioFacebook;
    private String instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.fiestaBackground));
        }

        imagenes = new ArrayList<>();

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        String type = "detallesCategoria";

        Bundle bundle = getIntent().getExtras();

        String  id = bundle.getString("id", "");

        backgroundWorker.execute(type, id);

        txtNombre = findViewById(R.id.txtNombreDetalles);
        txtSitio = findViewById(R.id.btnWeb);
        txtFacebook = findViewById(R.id.btnFacebook);
        txtTelefono = findViewById(R.id.btnLlamar);
        txtInstagram = findViewById(R.id.btnInstagram);

        ImageView btnHome = findViewById(R.id.btnHome);
        ImageView btnBack = findViewById(R.id.btnBack);

        btnHome.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        txtNombre.setOnClickListener(this);
        txtSitio.setOnClickListener(this);
        txtFacebook.setOnClickListener(this);
        txtTelefono.setOnClickListener(this);
        txtInstagram.setOnClickListener(this);
    }

    private void setUpCarousel(){
        PlutoIndicator plutoIndicator = findViewById(R.id.custom_indicator);
        PlutoView pluto = findViewById(R.id.plutoView);
        //pluto.setCustomIndicator(plutoIndicator);
        ImagenesAdapter adapter = new ImagenesAdapter(imagenes);
        pluto.create(adapter,getLifecycle());

    }

    public void fillDetallesList(String nombre, String sitio, String foto, String facebbok, String telefono, String instagram){
        if(nombre.equals("null")){
            nombre = "";
        }

        if(sitio.equals("null")){
            sitio = "";
            txtSitio.setVisibility(View.GONE);
        }

        if(foto.equals("null")){
            foto = "";
        }

        if(facebbok.equals("null")){
            facebbok = "";
            txtFacebook.setVisibility(View.GONE);
        }

        if(telefono.equals("null")){
            telefono = "";
            txtTelefono.setVisibility(View.GONE);
        }

        if(instagram.equals("null")){
            instagram = "";
            txtInstagram.setVisibility(View.GONE);
        }

        this.llamada = telefono;
        this.sitioWeb = sitio;
        this.sitioFacebook = facebbok;
        this.instagram = instagram;
    }

    public void fillImagenesList(int id, String foto, int size){
        imagenes.add(new Imagenes(id, foto));

        if(this.size < size - 1){
            this.size++;
        }else{
            setUpCarousel();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnHome:
                Intent home = new Intent(DetallesActivity.this, MainActivity.class);
                startActivity(home);
                finish();
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnFacebook:
                if (!sitioFacebook.startsWith("http://") && !sitioFacebook.startsWith("https://")) {
                    sitioFacebook = "http://" + sitioFacebook;
                }
                Intent browserFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse(sitioFacebook));
                startActivity(browserFacebook);
                break;
            case R.id.btnLlamar:
                Intent telefono = new Intent(Intent.ACTION_DIAL);
                telefono.setData(Uri.parse("tel:" + llamada));
                startActivity(telefono);
                break;
            case R.id.btnWeb:
                if (!sitioWeb.startsWith("http://") && !sitioWeb.startsWith("https://")) {
                    sitioWeb = "http://" + sitioWeb;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sitioWeb));
                startActivity(browserIntent);
                break;
            case R.id.btnInstagram:
                if (!instagram.startsWith("http://") && !instagram.startsWith("https://")) {
                    sitioWeb = "http://" + sitioWeb;
                }
                Intent instagramBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(instagram));
                startActivity(instagramBrowser);
                break;
        }
    }
}
