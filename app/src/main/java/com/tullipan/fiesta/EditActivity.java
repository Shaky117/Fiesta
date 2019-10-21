package com.tullipan.fiesta;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
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

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, IPickResult {

    private Button btnEdit;

    private Context context;
    private CircleImageView circleImageView;

    private EditText etNombre;
    private EditText etCorreo;
    private EditText etContra;
    private EditText etRepeat;

    private String foto64;

    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        context = this;

        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.fiestaBackground));
        }

        circleImageView = findViewById(R.id.circleImageView);
        etNombre = findViewById(R.id.etNombre);
        etContra = findViewById(R.id.etContra);
        etRepeat = findViewById(R.id.etRepetir);

        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);

        btnBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
    }

    public void editCompleted(boolean completed, String mensaje){
        if(completed){
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
        }
    }

    public void checkFieldsAndConnect(){
        String nombre = etNombre.getText().toString();
        String contra = etContra.getText().toString();
        String repeat = etRepeat.getText().toString();

            if(contra.equals(repeat)) {
                if (contra.length() >= 8) {
                    BackgroundWorker backgroundWorker = new BackgroundWorker(context);
                    String type = "editUser";
                    backgroundWorker.execute(type, nombre, contra, foto64, "foto.jpg");
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnEdit:
                checkFieldsAndConnect();
                break;
            case R.id.circleImageView:
                PickImageDialog.build(new PickSetup()).show(this);
                break;
        }
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            //getImageView().setImageBitmap(r.getBitmap());
            Bitmap bitmap = r.getBitmap();
            foto64 = ImageUtil.convert(bitmap);
            circleImageView.setImageBitmap(bitmap);
            //r.getPath();
        } else {
            //Handle possible errors
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
