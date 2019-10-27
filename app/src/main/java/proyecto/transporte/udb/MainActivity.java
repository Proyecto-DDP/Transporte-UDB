package proyecto.transporte.udb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import proyecto.transporte.udb.keepLogin.PreferenceUtils;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference unidades;
    private String unidadT;

    RecyclerView recyclerView;
    ArrayList<itemModel> arrayList;
    private int icons[] = {R.drawable.ic_bus,R.drawable.ic_bus,R.drawable.ic_bus,R.drawable.ic_bus};
    private String routes[];
    private int cantidadRutas=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        unidades = database.getReference("Unidades");

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Main);
        arrayList = new ArrayList<>();

        addRoute();

        ImageButton imageButton = (ImageButton) findViewById(R.id.closed_session);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateDialog();
            }
        });

        //Título de la pantalla
        View tt = findViewById(R.id.toolbar_main);
        TextView title = (TextView) tt.findViewById(R.id.toolTitle);
        title.setText("Pantalla principal");
    }
    public void showInfo(View view){
        Intent intent = new Intent(view.getContext(), show_info.class);
        startActivity(intent);
    }

    public void addRoute(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        unidades.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cantidadRutas = 0;
                for(DataSnapshot conteo: dataSnapshot.getChildren()){
                    cantidadRutas++;
                }
                routes = new String[cantidadRutas];
                int i=0;
                for(DataSnapshot placas : dataSnapshot.getChildren()){
                    unidadT = placas.getKey();
                    routes[i] = dataSnapshot.child(unidadT).child("Zona").getValue(String.class) + " - "+unidadT;
                    i++;
                }
                //No entiendo por que pero solo en este orden funciono
                for (int j = 0; j < routes.length; j++){
                    itemModel itemModel = new itemModel();
                    itemModel.setImage(icons[j]);
                    itemModel.setRouteN(routes[j]);
                    arrayList.add(itemModel);
                }
                routeAdapter adapter = new routeAdapter(getApplicationContext(), arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                PreferenceUtils.Clear(MainActivity.this);
                MainActivity.super.onBackPressed();
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