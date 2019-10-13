package proyecto.transporte.udb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void mantZona(View view){
        Intent intent = new Intent(this, mant_zona.class);
        startActivity(intent);
    }
    public void mantProp(View view){
        Intent intent = new Intent(this, mant_prop.class);
        startActivity(intent);
    }
    public void mantCondtuc(View view){
        Intent intent = new Intent(this, mant_conduc.class);
        startActivity(intent);
    }
}
