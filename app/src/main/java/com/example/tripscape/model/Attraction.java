package com.example.tripscape.model;

import android.util.Pair;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.List;

import static com.example.tripscape.model.Enums.*;
import static java.util.Arrays.asList;

public class Attraction implements Serializable {
    private Activity activity;
    private Location location;
    private int price;
    private boolean transportation;
    private String duration;
    private String startAddress;
    private String startTime;
    private List<String> seasonsAvailable;
    private String link;
    private List<TripDay> tripDays;
    private String title;
    private GeoPoint coordinates;

    public Attraction() {

    }

    public Attraction(String title, Activity activity, Location location, int price, boolean transportation ,String duration, String startAddress, String startTime,
                      List<String> seasonsAvailable, String link, List<TripDay> tripDays, GeoPoint coordinates) {
        this.title = title;
        this.activity = activity;
        this.location = location;
        this.price = price;
        this.transportation = transportation;
        this.duration = duration;
        this.startAddress = startAddress;
        this.startTime = startTime;
        this.seasonsAvailable = seasonsAvailable;
        this.link = link;
        this.tripDays = tripDays;
        this.coordinates = coordinates;
    }

    public Attraction(String title, Activity activity, Location location, int price, boolean transportation, String duration, String startAddress, String startTime, List<String> seasonsAvailable, String link) {
        this.title = title;
        this.activity = activity;
        this.location = location;
        this.price = price;
        this.transportation = transportation;
        this.duration = duration;
        this.startAddress = startAddress;
        this.startTime = startTime;
        this.seasonsAvailable = seasonsAvailable;
        this.link = link;
        this.tripDays = asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday);
        this.coordinates = new GeoPoint(40.689247, -64 - 044502);
    }

    public GeoPoint getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(GeoPoint coordinates) {
        this.coordinates = coordinates;
    }

    public List<TripDay> getTripDays() {
        return tripDays;
    }

    public void setTripDays(List<TripDay> tripDays) {
        this.tripDays = tripDays;
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

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startLocation) {
        this.startAddress = startLocation;
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

    @Override
    public String toString() {
        String res = "";
        res = String.format("Title: %s \nLocation: %s \nStartAddress:  %s, \nStartTime:  %s , \nPrice: %d  \nLink:  %s", title,location.toString(),startAddress, startTime, price, link);
        return res;
    }
}
