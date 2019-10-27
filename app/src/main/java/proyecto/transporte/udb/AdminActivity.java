package proyecto.transporte.udb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import proyecto.transporte.udb.keepLogin.PreferenceUtils;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton imageButton = (ImageButton) findViewById(R.id.closed_session);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateDialog();
            }
        });
        //Cambiando el título
        View tt = findViewById(R.id.toolbar_main);
        TextView title = (TextView) tt.findViewById(R.id.toolTitle);
        title.setText("Pantalla de administrador");
    }

    public void mantZona(View view){
        Intent intent = new Intent(this, mant_zona.class);
        startActivity(intent);
    }
    public void mantItinerario(View view){
        Intent intent = new Intent(this, mant_itinerario.class);
        startActivity(intent);
    }
    public void mantUnidades(View view){
        Intent intent = new Intent(this, mant_unidades.class);
        startActivity(intent);
    }
    public void mantUsuarios(View view){
        Intent intent = new Intent(this, mant_usuarios.class);
        startActivity(intent);
    }
    public void mantDoble(View view){
        Intent intent = new Intent(this, mant_doble.class);
        startActivity(intent);
    }

    //***Salir***

    public void onBackPressed()
    {
        CreateDialog();
    }

    private void CreateDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea cerrar la sesión?");
        builder.setCancelable(false);

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceUtils.Clear(AdminActivity.this);
                AdminActivity.super.onBackPressed();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }
}
