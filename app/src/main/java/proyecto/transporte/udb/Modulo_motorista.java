package proyecto.transporte.udb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import proyecto.transporte.udb.keepLogin.PreferenceUtils;

public class Modulo_motorista extends AppCompatActivity {

    private Button iniciar;
    private Button finalizar;
    private Button desperfectos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_motorista);

        iniciar = (Button) findViewById(R.id.transmitir_ubi);
        finalizar = (Button) findViewById(R.id.dejar_transmitir);
        desperfectos = (Button) findViewById(R.id.desperfectos);

        if (!runtime_permissions()){
            enable_buttons();
        }

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
        title.setText("Pantalla de motorista");
    }

    public void enable_buttons() {
        IniciarTransmision(iniciar);
        FinalizarTransmision(finalizar);
        ReportarDesperfecto(desperfectos);
    }

    public void ReportarDesperfecto(View view) {
    }

    public void FinalizarTransmision(View view) {
        Intent i = new Intent(this, GPS_Service.class);
        stopService(i);
    }

    public void IniciarTransmision(View view) {
        Intent i = new Intent(this, GPS_Service.class);
        startService(i);
    }

    //Este método revisa si los permisos necesarios para acceder a la ubicacion se encuentran aceptados, sino solicita acepatarlos
    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
            return true;
        }
        return false;
    }

    //Este metodo se encarga del manejo de recibir los resultados de la solicitud de permisos de parte del usuario
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                //Si los permisos son aceptados se activan los botones
                enable_buttons();
            }else{
                //Si los permisos son denegados se vuelven a solicitar
                runtime_permissions();
            }
        }
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
                PreferenceUtils.Clear(Modulo_motorista.this);
                FinalizarTransmision(finalizar);
                Modulo_motorista.super.onBackPressed();
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
