package proyecto.transporte.udb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
                    Log.d("Rutas encontradas",routes[i].toString());
                    Log.d("Iconos", Integer.toString(icons[i]));
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

}