package com.tullipan.fiesta;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistrarme;

    private Context context;

    private EditText etNombre;
    private EditText etCorreo;
    private EditText etContra;
    private EditText etRepetir;

    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
        }

        context = this;
        etContra = findViewById(R.id.etContra);
        etCorreo = findViewById(R.id.etCorreo);
        etNombre = findViewById(R.id.etNombre);
        etRepetir = findViewById(R.id.etRepetir);
        btnBack = findViewById(R.id.btnBack);
        btnRegistrarme = findViewById(R.id.btnRegister);

        btnRegistrarme.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    public void registerCompleted(boolean completed, String mensaje){
        if(completed){
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
        }
    }

    private void checkFieldsAndConnect(){
        String nombre = etNombre.getText().toString();
        String correo = etCorreo.getText().toString();
        String contra = etContra.getText().toString();
        String repetir = etRepetir.getText().toString();

        if(!nombre.equals("") && !correo.equals("") && !contra.equals("") && !repetir.equals("")){
            if(contra.equals(repetir)) {
                if (contra.length() >= 8) {
                    if(isValidEmail(correo)) {
                        BackgroundWorker backgroundWorker = new BackgroundWorker(context);
                        String type = "register";
                        backgroundWorker.execute(type, nombre, correo, contra, repetir);
                    }
                    else{
                        Toast.makeText(
                                getApplicationContext(),
                                "Favor de introducir un correo valido",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(
                            getApplicationContext(),
                            "Contraseña debe ser mayor a 8 caracteres",
                            Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(
                        getApplicationContext(),
                        "Las contraseñas no coinciden.",
                        Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(
                    getApplicationContext(),
                    "Favor de llenar todos los campos",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnRegister:
                checkFieldsAndConnect();
                break;
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
