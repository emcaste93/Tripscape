package com.example.tripscape.data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tripscape.model.Attraction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AttractionFirestore {
    private CollectionReference attractions;
    private static final String TAG = "Tripescape";

    public AttractionFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        attractions = db.collection("attractionsGermany");
    }

    public void addAttracionToCollection(Attraction attraction) {
        attractions.document().set(attraction);
    }

    public String addEmptyAttrationToCollection() {
        return attractions.document().getId();
    }

    public void updateAttraction(String id, Attraction attraction) {
        attractions.document(id).set(attraction);
    }

    public void getAttractions(final FirestoreDataCallback firestoreData) {
        AsyncTask<Void, Void, Boolean> task = new FirestoreTask(firestoreData).execute();
    }

    class FirestoreTask extends AsyncTask<Void, Void, Boolean> {
        boolean isSuccessful;
        boolean isComplete;
       FirestoreDataCallback callback;

        public FirestoreTask(FirestoreDataCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... String) {
            attractions.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    isSuccessful = task.isSuccessful();
                    if(task.isSuccessful()) {
                        for(DocumentSnapshot document: task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData().toString());
                            Attraction a = document.toObject(Attraction.class);
                            callback.addAttraction(a);
                        }
                    }
                    else {
                        Log.d(TAG, "Error getting documents ", task.getException());
                    }
                }
            });
            return isComplete && isSuccessful;
        }
    }
}
