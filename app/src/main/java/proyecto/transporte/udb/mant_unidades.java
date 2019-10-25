package proyecto.transporte.udb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class mant_unidades extends AppCompatActivity {
    //Arrays para guardar las entradas y salidas
    ArrayList<String> Entradas = new ArrayList<String>();
    ArrayList<String> Salidas = new ArrayList<String>();
    ChipGroup chipGroup1, chipGroup2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantunidades);

        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(mant_unidades.this);
        chipGroup1 = (ChipGroup) findViewById(R.id.cgIn);
        chipGroup2 = (ChipGroup) findViewById(R.id.cgOut);
        String[] entradas = new String[]{"08:00am"};
        for (String entrada : entradas){
            Chip chip = (Chip)inflater.inflate(R.layout.added_chip, null, false);
            chip.setText(entrada);
            chipGroup1.addView(chip);
        }
    }

}
