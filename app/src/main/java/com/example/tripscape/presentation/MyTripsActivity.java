package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripscape.R;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Trip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class MyTripsActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrips_layout);

        //initialise variables
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView_myTrips);
    }


    public void readFirestoreData () {
        Query query = FirebaseFirestore.getInstance()
                .collection("trips")
                .limit(50);
        ProgressDialog loadingDialog = ProgressDialog.show(this, "", "Loading data. Please wait...", true);

        query.get().
                addOnSuccessListener(documentSnapshots -> {
                    if (documentSnapshots.isEmpty()) {
                        //   Log.d(TAG, "onSuccess: LIST EMPTY");
                        return;
                    } else {
                        //Save data into Attraction class and initialise Selected Activities to All activities
                        List<Trip> trips = documentSnapshots.toObjects(Trip.class);
                    }
                    loadingDialog.dismiss();
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show());
    }
}
