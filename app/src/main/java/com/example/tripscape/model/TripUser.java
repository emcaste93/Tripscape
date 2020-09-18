package com.example.tripscape.model;

public class TripUser {
    private String name;
    private String email;
    private String uid;
    private static TripUser userInstance;

    public TripUser() {}

    public TripUser(String name, String email, String uid) {
        if (userInstance != null){
            //   throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public static TripUser getInstance(){
        //If there is no instance available... create new one
        if (userInstance == null){
            userInstance = new TripUser();
        }
        return userInstance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
