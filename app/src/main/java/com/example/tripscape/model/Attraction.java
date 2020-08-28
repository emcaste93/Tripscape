package com.example.tripscape.model;

import java.util.List;

import static com.example.tripscape.model.Enums.*;

public class Attraction {
    private Activity activity;
    private Location location;
    private int price;
    private boolean transportation;
    private String duration;
    private String startLocation;
    private String startTime;
    private List<String>  seasonsAvailable;
    private String link;

    private String title;

    public Attraction(String title, Activity activity, Location location, int price, boolean transportation, String duration, String startLocation, String startTime, List<String> seasonsAvailable, String link) {
        this.title = title;
        this.activity = activity;
        this.location = location;
        this.price = price;
        this.transportation = transportation;
        this.duration = duration;
        this.startLocation = startLocation;
        this.startTime = startTime;
        this.seasonsAvailable = seasonsAvailable;
        this.link = link;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getTransportation() {
        return transportation;
    }

    public void setTransportation(boolean transportation) {
        this.transportation = transportation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<String> getSeasonsAvailable() {
        return seasonsAvailable;
    }

    public void setSeasonsAvailable(List<String> seasonsAvailable) {
        this.seasonsAvailable = seasonsAvailable;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
