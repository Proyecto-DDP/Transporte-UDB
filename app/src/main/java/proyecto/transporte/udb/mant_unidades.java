package proyecto.transporte.udb;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class mant_unidades extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference unidades, zonas, motoristas, propietarios, itinerarios;
    private TextInputEditText placa;
    private AutoCompleteTextView tipo, propietario, motorista, zona, itinerario;
    private String[] TIPOS = {"Microbus","Bus"};
    private String[] PROPIETARIOS, MOTORISTAS, ZONAS, ITINERARIOS;
    private int totPropietarios, totMotoristas, totZonas, totItinerarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantunidades);

        database = FirebaseDatabase.getInstance();
        unidades = database.getReference("Unidades");
        zonas = database.getReference("Zonas");
        motoristas = database.getReference("Motoristas");
        propietarios = database.getReference("Propietarios");
        itinerarios = database.getReference("Itinerarios");

        placa = findViewById(R.id.txtinp_placa);
        tipo = findViewById(R.id.filled_tipoU);
        propietario = findViewById(R.id.dd_prop);
        motorista = findViewById(R.id.dd_moto);
        zona = findViewById(R.id.filled_zonas);
        itinerario = findViewById(R.id.itinerario);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        cargarInformacion();
    }

    //Llenado de los dropdown con informacion de firebase
    private void cargarInformacion() {
        propietarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot conteoPropietarios : dataSnapshot.getChildren()) {
                    totPropietarios++;
                }
                PROPIETARIOS = new String[totPropietarios];
                for (DataSnapshot conteoPropietarios : dataSnapshot.getChildren()) {
                    PROPIETARIOS[i] = conteoPropietarios.getValue(String.class);
                    Log.d("---------PROPIETARIOS",PROPIETARIOS[i]);
                    i++;
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, PROPIETARIOS);
                propietario.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        motoristas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot conteoMotoristas : dataSnapshot.getChildren()) {
                    totMotoristas++;
                }
                MOTORISTAS = new String[totMotoristas];
                for (DataSnapshot conteoMotoristas : dataSnapshot.getChildren()) {
                    MOTORISTAS[i] = conteoMotoristas.getValue(String.class);
                    i++;
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, MOTORISTAS);
                motorista.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        zonas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot conteoZonas : dataSnapshot.getChildren()) {
                    totZonas++;
                }
                ZONAS = new String[totZonas];
                for (DataSnapshot conteoZonas : dataSnapshot.getChildren()) {
                    ZONAS[i] = conteoZonas.getValue(String.class);
                    i++;
                }
                ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, ZONAS);
                zona.setAdapter(adapter3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        itinerarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot conteoItinerarios : dataSnapshot.getChildren()) {
                    totItinerarios++;
                }
                //Falta programarlo
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, TIPOS);
        tipo.setAdapter(adapter5);
    }

    public void buscarUnidad(View view){
        final String placaProv = placa.getText().toString().toUpperCase();

        if ( placaProv != null || placaProv != ""){
            unidades.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot unidad = dataSnapshot.child(placaProv);
                    tipo.setText(unidad.child("Tipo").getValue(String.class));
                    propietario.setText(unidad.child("Propietario").child("Nombre").getValue(String.class));
                    motorista.setText(unidad.child("Motorista").child("Nombre").getValue(String.class));
                    zona.setText(unidad.child("Zona").getValue(String.class));
                    itinerario.setText("Nada");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
