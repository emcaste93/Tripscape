package com.example.tripscape.model;

import android.util.Log;

import com.example.tripscape.data.TripescapeFirestore;
import com.example.tripscape.data.AttractionList;
import com.example.tripscape.data.FirestoreData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirestoreDataAdapterImpl implements FirestoreDataAdapter {
    private FirestoreData firestoreData;
    private static FirestoreDataAdapterImpl firestoreDataInstance;
    TripescapeFirestore attractionFirestore;

    public FirestoreDataAdapterImpl() {
        if (firestoreDataInstance != null){
        //    throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        firestoreData = new FirestoreData();
        attractionFirestore = new TripescapeFirestore();
    }

    public static FirestoreDataAdapterImpl getInstance(){
        //If there is no instance available... create new one
        if (firestoreDataInstance == null){
            firestoreDataInstance = new FirestoreDataAdapterImpl();
        }
        return firestoreDataInstance;
    }

    @Override
    public List<Enums.Location> getTripLocations() {
        return firestoreData.getTripLocations();
    }

    @Override
    public boolean isAttractionCompatibleWithTripStartDate(Attraction a, Date startDate) {
        return firestoreData.isAttractionCompatibleWithTripStartDate(a, startDate);
    }

    @Override
    public ArrayList<Attraction> getAttractionsForLocation(Enums.Location location, Date startDate) {
        return firestoreData.getAttractionsForLocation(location, startDate);
    }

    @Override
    public ArrayList<Attraction> getAttractionsForLocation(Enums.Location location, Date startDate, Date endDate, int maxBudget) {
        return firestoreData.getAttractionsForLocation(location, startDate, endDate, maxBudget);
    }

    @Override
    public ArrayList<Enums.Activity> getActivitiesForLocation(Enums.Location location, Date startDate) {
        return firestoreData.getActivitiesForLocation(location, startDate);
    }

    @Override
    public AttractionList getAttractionList() {
        return new AttractionList();
    }

    @Override
    public void generateFirestoreData() {
        //Generate Data into Firestore
        AttractionList attractionList = getAttractionList();
        attractionFirestore.generateFirestoreData(attractionList);
    }

    @Override
    public void saveTripData() {
        attractionFirestore.saveTripData(Trip.getInstance());
    }

    @Override
    public void deleteTrip(Trip trip) {
        attractionFirestore.removeTrip(trip.getId());
    }

    @Override
    public void saveUser(final TripUser user) {
        attractionFirestore.saveUser(user);
    }

    @Override
    public ArrayList<Enums.Activity> getAllActivities() {
        return firestoreData.getAllActivities();
    }

    public void setFirestoreDataAttractionList(List<Attraction> attractions) {
        firestoreData.setAttractionList(attractions);
    }
}
