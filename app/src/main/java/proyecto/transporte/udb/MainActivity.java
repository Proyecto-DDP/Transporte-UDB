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
    int icons[] = {R.drawable.ic_bus,R.drawable.ic_delete,R.drawable.ic_search,R.drawable.ic_search};
    String routes[] = new String[10];

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
        obtenerRutas();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        for (int i = 0; i < routes.length; i++){
            itemModel itemModel = new itemModel();
            itemModel.setImage(icons[i]);
            itemModel.setRouteN(routes[i]);
            arrayList.add(itemModel);
        }
        routeAdapter adapter = new routeAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void obtenerRutas() {
        unidades.addValueEventListener(new ValueEventListener() {
            int i=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot placas : dataSnapshot.getChildren()){
                    unidadT = placas.getKey();
                    routes[i] = dataSnapshot.child(unidadT).child("Zona").getValue(String.class);
                    routes[i] += " - "+unidadT;
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}