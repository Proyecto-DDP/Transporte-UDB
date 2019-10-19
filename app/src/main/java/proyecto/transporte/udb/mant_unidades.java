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

        obtChips();
    }

    public void obtChips(){
        Button insertB = findViewById(R.id.addU);
        insertB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChipGroup chgIn = findViewById(R.id.cgIn);
                ChipGroup chgOut = findViewById(R.id.cgOut);

                int countIn, countOut;
                countIn = chgIn.getChildCount();
                countOut = chgOut.getChildCount();

                if (countIn != 0 || countOut != 0){
                    int i = 0;
                    while (i < countIn){
                        Chip chipIn = (Chip) chgIn.getChildAt(i);
                        Chip chipOut = (Chip) chgOut.getChildAt(i);
                        if(chipIn.isChecked()){
                            Entradas.add(chipIn.getText().toString());
                        }
                        if (chipOut.isChecked()){}
                        Salidas.add(chipOut.getText().toString());
                        i++;
                    }

                }
                Toast toast = Toast.makeText(getApplicationContext(),Entradas.toString(),Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
