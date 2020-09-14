package com.example.tripscape.model;

import com.example.tripscape.data.FirestoreData;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.tripscape.model.Enums.*;

public class Trip implements Serializable {

    private int numPersons, budget, lastBudget;
    private int totalPrice;
    private Date startDate, endDate, lastStartDate, lastEndDate;
    private ArrayList<Activity> desiredActivities;
    private ArrayList<Attraction> selectedAttractions;
    private Location destination;
    private static Trip tripInstance;
    private Location lastDestination;

    public Trip() {
        if (tripInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        numPersons = 2;
        budget = 0;
        totalPrice = 0;
        startDate = new Date();
        endDate = new Date();
        destination = null;
        desiredActivities = new ArrayList<>();
        selectedAttractions = new ArrayList<>();
        lastBudget = 0;
    }

    public static Trip getInstance(){
        //If there is no instance available... create new one
        if (tripInstance == null){
            tripInstance = new Trip();
        }
        return tripInstance;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Attraction> getSelectedAttractions() {
        return selectedAttractions;
    }
    public ArrayList<Activity> getDesiredActivities() {
        return desiredActivities;
    }

    public void setSelectedAttractions(ArrayList<Attraction> attractions) {
        this.selectedAttractions = attractions;
    }

    public void setDesiredActivities(ArrayList<Activity> activities) {
        this.desiredActivities = activities;
    }

    public void addPerson() {
        numPersons ++;
    }

    public void removePerson() {
        numPersons --;
    }

    public void initialiseSelectedAttractions() {
        if (lastDestination == null || lastDestination != destination || lastBudget != budget
                || lastStartDate != startDate || lastEndDate != endDate) {
            totalPrice = 0;
            selectedAttractions = FirestoreDataAdapterImpl.getInstance().getAttractionsForLocation(destination, startDate, endDate, budget);
            for(Attraction a: selectedAttractions) {
                totalPrice += (a.getPrice() * numPersons);
            }
            lastDestination = destination;
            lastBudget = budget;
        }
    }

    /** Adds an attraction to the selected attractions list and updates the total price */
    public void addSelectedAttraction(Attraction attraction) {
        selectedAttractions.add(attraction);
        totalPrice += (attraction.getPrice() * numPersons);
    }

    /** Removes an attraction from the selected attractions list and updates the total price */
    public void removeSelectedAttraction(Attraction attraction) {
        selectedAttractions.remove(attraction);
        totalPrice -= (attraction.getPrice() * numPersons);
    }

    public String getTripData() {
        String data = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        data += "Start Date: " + simpleDateFormat.format(startDate);
        data += "\nEnd Date: " + simpleDateFormat.format(endDate);
        data += "\nDestination: " + destination.toString();
        data += "\n\n*** Selected attractions: *** ";
        int count = 0;
        for(Attraction a: selectedAttractions) {
            count ++;
            data += "\n " + count + ") " + a.toString();
        }
        return data;
    }

}
