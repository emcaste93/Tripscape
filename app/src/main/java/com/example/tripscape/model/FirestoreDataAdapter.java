package com.example.tripscape.model;

import com.example.tripscape.data.AttractionList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface FirestoreDataAdapter {
    List<Enums.Location> getTripLocations();
    boolean isAttractionCompatibleWithTripStartDate(Attraction a, Date startDate);
    ArrayList<Attraction> getAttractionsForLocation(Enums.Location location, Date startDate);
    ArrayList<Attraction> getAttractionsForLocation(Enums.Location location, Date startDate, Date endDate, int maxBudget);
    ArrayList<Enums.Activity> getActivitiesForLocation(Enums.Location location, Date startDate);
    AttractionList getAttractionList();
    void generateFirestoreData();
    void saveTripData();
    void deleteTrip(Trip trip);
    void saveUser(final TripUser user);
    ArrayList<Enums.Activity> getAllActivities();
}
