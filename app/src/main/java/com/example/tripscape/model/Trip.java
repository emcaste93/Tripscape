package com.example.tripscape.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Trip implements Serializable {

    private int numPersons, budget;
    private Date startDate, endDate;
    private ArrayList<String> activities;
    private static Trip tripInstance;

    public Trip() {
        if (tripInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        numPersons = 2;
        budget = 500;
        startDate = new Date();
        endDate = new Date();
        activities = new ArrayList<>();
    }

    public static Trip getInstance(){
        if (tripInstance == null){ //if there is no instance available... create new one
            tripInstance = new Trip();
        }
        return tripInstance;
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

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }

    public void addPerson() {
        numPersons ++;
    }

    public void removePerson() {
        numPersons --;
    }

}
