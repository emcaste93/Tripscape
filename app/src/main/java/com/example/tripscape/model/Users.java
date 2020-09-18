package com.example.tripscape.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Users {
    private static final String TAG = "Tripscape";

    public static void saveUser(final FirebaseUser firebaseUser) {
        User user = new User(firebaseUser.getDisplayName(),firebaseUser.getEmail());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        try {
            db.collection("users").document(firebaseUser.getUid()).set(user);
        }
        catch (Exception e) {
            Log.d(TAG, "Error when storing the user into FirebaseFirestore: " +e.getMessage());
        }
    }
}
