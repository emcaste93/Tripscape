package com.example.tripscape.model;

import com.example.tripscape.data.AttractionFirestore;
import com.example.tripscape.data.AttractionList;
import com.example.tripscape.data.FirestoreData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirestoreDataAdapterImpl implements FirestoreDataAdapter {
    private FirestoreData firestoreData;
    private static FirestoreDataAdapterImpl firestoreDataInstance;

    public FirestoreDataAdapterImpl() {
        if (firestoreDataInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        firestoreData = new FirestoreData();
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
        AttractionFirestore attractionFirestore = new AttractionFirestore();
        attractionFirestore.generateFirestoreData(attractionList);
    }

    @Override
    public ArrayList<Enums.Activity> getAllActivities() {
        return firestoreData.getAllActivities();
    }

    public void setFirestoreDataAttractionList(List<Attraction> attractions) {
        firestoreData.setAttractionList(attractions);
    }
}
