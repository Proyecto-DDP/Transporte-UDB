package com.example.transporteudb_v04;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceUnidad;
    private Unidad unidad;

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceUnidad = mDatabase.getReference("Unidad");
    }

    public void readUnidades(final DataStatus dataStatus){
        mReferenceUnidad.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                unidad = dataSnapshot.getValue(Unidad.class);
                dataStatus.DataIsLoaded(unidad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface DataStatus{
        void DataIsLoaded(Unidad unidad);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
}
