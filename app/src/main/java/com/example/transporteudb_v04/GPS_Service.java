package com.example.transporteudb_v04;

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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GPS_Service extends Service {

    private LocationListener listner;
    private LocationManager locationManager;

    FirebaseDatabase database;
    DatabaseReference unidadReference;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        database = FirebaseDatabase.getInstance();
        unidadReference = database.getReference("Unidad");

        listner = new LocationListener() {

            //Cuando hay un cambio en la ubicacion se llama el metodo onLocationChanged
            @Override
            public void onLocationChanged(Location location) {

                /*
                //Aqui debo INSERTAR las coordenadas en FIREBASE
                unidadReference.child("Latitud").setValue(location.getLatitude());
                unidadReference.child("Longitud").setValue(location.getLongitude());
                 */


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                //Si el usuario tiene desactivado el gps, el siguiente codigo lo llevara a los ajustes para que lo active
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //Solicita actualizar la ubicacion cada 3 segundos
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listner);
    }
}
