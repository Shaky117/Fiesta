package com.tullipan.fiesta;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NoticiaActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    private TextView txtTitulo;
    private TextView txtDescripcion;
    private ImageView ivNoticia;
    private ImageView btnHome;
    private Button btnProveedor;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        context = this;

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.fiestaBackground));
        }

        Bundle bundle = getIntent().getExtras();

        id = bundle.getString("id");
        String titulo = bundle.getString("titulo");
        String descripcion = bundle.getString("descripcion");
        String imagen = bundle.getString("imagen");

        txtDescripcion = findViewById(R.id.tvDescripcion);
        txtTitulo = findViewById(R.id.tvTitulo);
        ivNoticia = findViewById(R.id.ivNoticia);
        btnProveedor = findViewById(R.id.btnProveedor);
        btnHome = findViewById(R.id.btnHome);

        if(id == null && id.equals("") && id.equals("null")){
            btnProveedor.setVisibility(View.GONE);
        }

        String path = "http://admin.easyparty.mx/img/uploads/" + imagen;

        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);
        Picasso.with(context).load(path).into(ivNoticia);

        btnProveedor.setOnClickListener(this);
        btnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnProveedor:
                Intent intent = new Intent(NoticiaActivity.this, DetallesActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.btnHome:
                finish();
                break;
        }
    }
}
