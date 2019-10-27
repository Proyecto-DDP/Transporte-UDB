package proyecto.transporte.udb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mant_zona extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference zonas;
    private int cantidadZonas;
    private TextInputEditText codigo;
    private TextInputEditText nombre;
    private AutoCompleteTextView listaZonas;
    private String ZONAS[];
    private String CODIGO[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantzona);

        database = FirebaseDatabase.getInstance();
        zonas = database.getReference("Zonas");

        codigo = (TextInputEditText) findViewById(R.id.txtinp_codigo) ;
        nombre = (TextInputEditText) findViewById(R.id.txtinp_nombre);
        listaZonas = findViewById(R.id.filled_zonas);
        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        zonas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cantidadZonas = 0;
                int i = 0;
                for(DataSnapshot conteo : dataSnapshot.getChildren()){
                    cantidadZonas++;
                }
                ZONAS = new String[cantidadZonas];
                CODIGO = new String[cantidadZonas];
                for(DataSnapshot znas : dataSnapshot.getChildren()){
                    CODIGO[i] = znas.getKey();
                    ZONAS[i] = znas.getValue(String.class);
                    i++;
                }
                Log.d("----------------ZONAS", ZONAS[0]);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(
                                getApplicationContext(),
                                R.layout.dropdown_item,
                                ZONAS);

                if (listaZonas != null) {
                    listaZonas.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listaZonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("--------------ZONA", CODIGO[position]);
                Log.d("------------------VIEW",view.toString());
                Log.d("--------------POSITION", String.valueOf(position));
                Log.d("--------------ID", String.valueOf(id));
                Log.d("--------------TEXTO", listaZonas.getText().toString());
                codigo.setText(CODIGO[position]);
                nombre.setText(ZONAS[position]);
            }
        });
    }

    public void AgregarZona(View view){
        String nuevaZona = nombre.getText().toString();
        final String nuevoCodigo = codigo.getText().toString();
        Boolean zonaExistente = detectar(nuevaZona);

        if (zonaExistente){
            Toast toast = Toast.makeText(getApplicationContext(),"Zona existente", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            zonas.child(nuevoCodigo).setValue(nuevaZona);
            Toast toast = Toast.makeText(getApplicationContext(),"Zona agregada", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void EliminarZona(View view){
        String eliminarZona = nombre.getText().toString();
        String eliminarCodigo = codigo.getText().toString();
        Boolean zonaExistente = detectar(eliminarZona);

        if (zonaExistente){
            zonas.child(eliminarCodigo).removeValue();
            Toast toast = Toast.makeText(getApplicationContext(),"Zona eliminada", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Toast toast = Toast.makeText(getApplicationContext(),"Zona no existente", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private Boolean detectar(String valor){
        Boolean bandera = false;
        for(String zna : ZONAS){
            if (valor.equals(zna)){
                bandera = true;
                break;
            }
        }
        return bandera;
    }
}
