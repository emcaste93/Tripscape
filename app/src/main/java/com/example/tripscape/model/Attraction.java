package com.example.tripscape.model;

import java.util.List;

public class Attraction {
    private String name;
    private Enums.Location location;
    private int price;
    private boolean transportation;
    private String duration;
    private String startLocation;
    private String startTime;
    private List<String>  seasonsAvailable;
    private String link;

    public Attraction(String name, Enums.Location location, int price, boolean transportation, String duration, String startLocation, String startTime, List<String> seasonsAvailable, String link) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.transportation = transportation;
        this.duration = duration;
        this.startLocation = startLocation;
        this.startTime = startTime;
        this.seasonsAvailable = seasonsAvailable;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enums.Location getLocation() {
        return location;
    }

    public void setLocation(Enums.Location location) {
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
