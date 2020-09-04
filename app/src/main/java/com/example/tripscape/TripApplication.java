package com.example.tripscape;

import android.app.Application;

import com.example.tripscape.data.AttractionFirestore;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TripApplication extends Application {
    public AttractionFirestore attractions;

    @Override public void onCreate() {
        super.onCreate();
        attractions = new AttractionFirestore();
        Query query = FirebaseFirestore.getInstance()
                .collection("attractions")
                .limit(50);
    }
}
