package com.example.tripscape.data;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Trip;
import com.example.tripscape.presentation.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

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

    public void generateFirestoreData(AttractionList attractionList) {
        for(int id = 0; id < attractionList.getSize(); id++) {
            attractions.add(attractionList.getElementAt(id));
        }
    }
}
