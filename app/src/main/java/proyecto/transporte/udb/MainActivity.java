package proyecto.transporte.udb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<itemModel> arrayList;
    int icons[] = {R.drawable.ic_bus,R.drawable.ic_delete,R.drawable.ic_search};
    String routes[] = {"Santa Tecla - UDB", "Zacamil - UDB","Prueba - UDB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        for (int i = 0; i < routes.length; i++){
            itemModel itemModel = new itemModel();
            itemModel.setImage(icons[i]);
            itemModel.setRouteN(routes[i]);
            arrayList.add(itemModel);
        }
        routeAdapter adapter = new routeAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }
}