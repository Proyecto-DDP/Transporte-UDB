package proyecto.transporte.udb;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GPS_Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private FirebaseDatabase database;
    private DatabaseReference unidades;
    private DatabaseReference usuarios;

    private LocationListener listener;
    private LocationManager locationManager;

    private Double longitud;
    private Double latitud;

    private String motorista;
    private String placa;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        unidades = database.getReference("Unidades");
        usuarios = database.getReference("Usuarios");

        Login login = new Login();
        motorista = login.usuarioMotorista;

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placa = dataSnapshot.child(motorista).child("Unidad").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                unidades.child(placa).child("Ubicacion").child("Latitud").setValue(location.getLatitude());
                unidades.child(placa).child("Ubicacion").child("Longitud").setValue(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,0,listener);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null)
            locationManager.removeUpdates(listener);
    }

}
