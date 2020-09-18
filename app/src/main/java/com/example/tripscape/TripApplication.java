package com.example.tripscape;

import android.app.Application;

import com.example.tripscape.data.TripescapeFirestore;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TripApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
    }
}
