package com.example.tripscape.data;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Trip;
import com.example.tripscape.model.TripUser;
import com.example.tripscape.presentation.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static android.content.ContentValues.TAG;

public class TripescapeFirestore {
    private CollectionReference attractions;
    private CollectionReference trips;
    private static final String TAG = "Tripescape";

    public TripescapeFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        attractions = db.collection("attractionsGermany");
        trips = db.collection("tripsGermany");
    }

    public void addAttracionToCollection(Attraction attraction) {
        attractions.document().set(attraction);
    }

    public void addTripToCollection(Trip trip) {
        trips.document().set(trip);
    }

    public String addEmptyAttrationToCollection() {
        return attractions.document().getId();
    }

    public String addEmptyTripToCollection() {
        return trips.document().getId();
    }

    public void updateAttraction(String id, Attraction attraction) {
        attractions.document(id).set(attraction);
    }

    public void updateTrip(String id, Trip trip) {
        attractions.document(id).set(trip);
    }

    public void saveTripData(Trip trip) {
        if(trip.getUserId() == "") {
            trip.setUserId(TripUser.getInstance().getUid());
        }
        trips.add(trip);
    }

    public void generateFirestoreData(AttractionList attractionList) {
        for(int id = 0; id < attractionList.getSize(); id++) {
            attractions.add(attractionList.getElementAt(id));
        }
    }

    public void saveUser(final TripUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        try {
            db.collection("users").document(user.getUid()).set(user);
        }
        catch (Exception e) {
            Log.d(TAG, "Error when storing the user into FirebaseFirestore: " +e.getMessage());
        }
    }


}
