package proyecto.transporte.udb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText; 

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference referencia;


    public String username;
    public String password;
    public String tipo;

    private EditText Usuario;
    private EditText Password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Usuario = findViewById(R.id.carnet);
        Password = findViewById(R.id.pass);

        database = FirebaseDatabase.getInstance();
        referencia = database.getReference();

    }


    public void Identify(final String uID, final String uPASS){

        referencia.child("Usuarios").child(uID).addListenerForSingleValueEvent(
                new ValueEventListener () {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Lo ENCUENTRA
                        String TruePassword = dataSnapshot.child("Pass").getValue(String.class);
                        tipo = dataSnapshot.child("Tipo").getValue(String.class);
                        if (uPASS.equals(TruePassword))
                        {
                            switch (tipo){
                                case "Usuario":
                                    ActividadSig();
                                    break;
                                case "Motorista":
                                    ActividadMotorista();
                                    break;
                                    default:
                                        break;
                            }
                        }
                        else
                        {
                            CrearDialogo("Error", "Usuario o password incorrecto");

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Por si falla
                        CrearDialogo("Error", "Usuario o password incorrecto");
                    }
                });
    }




    //En respuesta al botón
    public void nextActivity(View view) {
        // Intent intent = new Intent(this, MainActivity.class);

        // startActivity(intent);

      String  username = Usuario.getText().toString();
      String  password = Password.getText().toString();
      //  CrearDialogo(username, password);

        Identify(username, password);
    }

    public void CrearDialogo(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void ActividadSig()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ActividadMotorista(){
        Intent intent = new Intent(this, Modulo_motorista.class);
        startActivity(intent);
    }
}