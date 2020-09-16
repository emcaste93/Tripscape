package com.example.tripscape.presentation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripscape.R;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Trip;
import com.example.tripscape.model.TripsAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class MyTripsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Trip> myTrips;
    private TripsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytrips_layout);

        //initialise variables
        init();
        getMyTrips();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView_myTrips);
    }

    //TODO: Modify this and filter by user
    private void getMyTrips() {
        readFirestoreData();
    }

    private void initRecyclerVieW() {
        //TODO: Order Trip list by startDate
        adapter = new TripsAdapter(this, myTrips);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void readFirestoreData () {
        Query query = FirebaseFirestore.getInstance()
                .collection("tripsGermany")
                .limit(50);
        ProgressDialog loadingDialog = ProgressDialog.show(this, "", "Loading data. Please wait...", true);

        query.get().
                addOnSuccessListener(documentSnapshots -> {
                    if (documentSnapshots.isEmpty()) {
                        //   Log.d(TAG, "onSuccess: LIST EMPTY");
                        return;
                    } else {
                        //Save data into Attraction class and initialise Selected Activities to All activities
                        myTrips = documentSnapshots.toObjects(Trip.class);
                        initRecyclerVieW();
                    }
                    loadingDialog.dismiss();
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show());
    }
}
