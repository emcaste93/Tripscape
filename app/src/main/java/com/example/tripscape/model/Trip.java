package com.example.tripscape.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Trip implements Serializable {

    private int numPersons, budget;
    private Date startDate, endDate;
    private ArrayList<String> activities;

    public Trip() {
        numPersons = 2;
        budget = 500;
        startDate = new Date();
        endDate = new Date();
        activities = new ArrayList<>();
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


}
