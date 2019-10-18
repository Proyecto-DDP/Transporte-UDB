package proyecto.transporte.udb;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FirebaseDatabase database;
    private DatabaseReference unidades;
    private  DatabaseReference usuarios;

    private Double latitud, longitud;
    private LatLng userLocation;
    private Marker usuario;

    //*********************ESTA INFO LA DEBO DE TOMAR DE FIREBASE Y EL BOTON ES QUIEN ME LA PASA********************//
    private String motorista;
    private String placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        database = FirebaseDatabase.getInstance();
        unidades = database.getReference("Unidades");
        usuarios = database.getReference("Usuarios");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        usuario = mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Mi ubicacion")
        );
        Login login = new Login();
        motorista = login.usuarioMotorista;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placa = dataSnapshot.child(motorista).child("Unidad").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        unidades.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                latitud = dataSnapshot.child(placa).child("Ubicacion").child("Latitud").getValue(Double.class);
                longitud = dataSnapshot.child(placa).child("Ubicacion").child("Longitud").getValue(Double.class);
                userLocation = new LatLng(latitud,longitud);

                usuario.setPosition(userLocation);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("GPSDebug", "loadPost:onCancelled", databaseError.toException());
            }
        });

        // Add a marker in Sydney and move the camera

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

    }
}
