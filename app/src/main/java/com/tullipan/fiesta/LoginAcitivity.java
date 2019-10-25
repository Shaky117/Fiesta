package com.tullipan.fiesta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginAcitivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    private Button btnLogin;

    private Context context;

    private EditText etContra;
    private EditText etCorreo;

    private ProgressBar progressBar;

    private TextView btnForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);

        SharedPreferences sharedPreferences = getSharedPreferences("MyValuesLogin", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if(!token.equals("")){
            Intent bypass = new Intent(LoginAcitivity.this, MainScreen.class);
            startActivity(bypass);
            finish();
        }

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
        }

        context = this;
        btnForgot = findViewById(R.id.btnForgot);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        etContra = findViewById(R.id.etContra);
        etCorreo = findViewById(R.id.etCorreo);
        progressBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnForgot.setOnClickListener(this);
    }

    private void checkFieldsAndConnect(){
        String correo = etCorreo.getText().toString();
        String contra = etContra.getText().toString();

        if(!correo.equals("") && !contra.equals("")){
            BackgroundWorker login = new BackgroundWorker(context);
            String type = "login";
            login.execute(type, correo, contra);
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void completedLogin(boolean completed){
        if(completed){
            Intent login = new Intent(LoginAcitivity.this, MainScreen.class);
            startActivity(login);
            btnLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            finish();
        }else{
            btnLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Correo o contraseña son incorrectas", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnForgot:
                break;
            case R.id.btnLogin:
                btnLogin.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                checkFieldsAndConnect();
                break;
            case R.id.btnRegister:
                Intent register = new Intent(LoginAcitivity.this, RegisterActivity.class);
                startActivity(register);
                break;
        }
    }
}
