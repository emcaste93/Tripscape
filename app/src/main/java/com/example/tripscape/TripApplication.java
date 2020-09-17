package com.example.tripscape;

import android.app.Application;

import com.example.tripscape.data.TripescapeFirestore;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TripApplication extends Application {
    public TripescapeFirestore attractions;

    @Override public void onCreate() {
        super.onCreate();
        attractions = new TripescapeFirestore();
        Query query = FirebaseFirestore.getInstance()
                .collection("attractionsGermany")
                .limit(50);
    }
}
