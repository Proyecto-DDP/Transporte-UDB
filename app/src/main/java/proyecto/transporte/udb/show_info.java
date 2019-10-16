package proyecto.transporte.udb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class show_info extends AppCompatActivity {
    int rotationAngle = 0;
    ChipGroup chipGroup1, chipGroup2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        //Flecha para regresar
        Toolbar toolbar = findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        final View expandable = findViewById(R.id.expandable);
        final ImageButton b = findViewById(R.id.expand_button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandable.getHeight() == 0){
                    expand(expandable, 500,200);
                    //Función para que gire
                    rotationAngle = rotationAngle == 0 ? 180 : 0;  //toggle
                    b.animate().rotation(rotationAngle).setDuration(500).start();
                }else if(expandable.getHeight() > 0){
                    collapse(expandable, 500, 0);
                    //Función para que gire
                    rotationAngle = rotationAngle == 0 ? 180 : 0;  //toggle
                    b.animate().rotation(rotationAngle).setDuration(500).start();
                }
            }
        });


        //Creación de los chips
        LayoutInflater inflater = LayoutInflater.from(show_info.this);
        chipGroup1 = (ChipGroup) findViewById(R.id.cgIn);
        chipGroup2 = (ChipGroup) findViewById(R.id.cgOut);
        String[] entradas = new String[]{"08:00am"};
        for (String entrada : entradas){
            Chip chip = (Chip)inflater.inflate(R.layout.added_chip, null, false);
            chip.setText(entrada);
            chipGroup1.addView(chip);
        }
    }
    //Animación para expandir
    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
    //Animación para retraer
    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public  void showMapa(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

}