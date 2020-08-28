package com.example.tripscape.data;

import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tripscape.model.Enums.*;
import static java.util.Arrays.asList;

public class FirestoreData {
    private static List<Attraction> attractions;

    public static void generateAttractionsData() {
        Attraction attraction1 =
                new Attraction(Activity.Hiking, Location.Munich, 20, true, "6h","Bahnhofplatz, Munich, 80335"
                        ,"08:30", asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString()), "https://chiemsee-sailingcenter.de");
        Attraction attraction2 =
                new Attraction(Activity.Sailing, Enums.Location.Munich, 30, true, "6h","Bahnhofplatz, Munich, 80335"
                        ,"10:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://chiemsee-sailingcenter.de");
        Attraction attraction3 =
                new Attraction(Activity.Sightseeing, Enums.Location.Munich, 30, false, "3h","Marienplatz, Munich, 80331"
                        ,"10:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://chiemsee-sailingcenter.de");
        Attraction attraction4 =
                new Attraction(Activity.Skiing, Enums.Location.Munich, 50, true, "12j","Bahnhofplatz, Munich, 80335"
                        ,"08:00", Arrays.asList(Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://https://www.skiwelt.at/en/individual-tariff.html");
        Attraction attraction5 =
                new Attraction(Activity.Canoeing, Location.Hamburg, 10, false, "2h","Rathausmarkt, Hamburg, 20095"
                        ,"11:00", asList(Season.Summer.toString(), Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
        Attraction attraction6 =
                new Attraction(Activity.Sailing, Enums.Location.Hamburg, 10, false, "4h","Rathausmarkt, Hamburg, 20095"
                        ,"12:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
        Attraction attraction7 =
                new Attraction(Activity.Sightseeing, Enums.Location.Berlin, 15, true, "3h","Alexanderplatz, Berlin, 10178"
                        ,"16:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.getyourguide.de/activity/berlin-l17/berlin-hop-on-hop-off-sightseeing-busfahrt-mit-live-guide-t272147?utm_force=0");
        Attraction attraction8 =
                new Attraction(Activity.Canoeing, Location.Berlin, 12, false, "3h","Alexanderplatz, Berlin, 10178"
                        ,"15:00", asList(Season.Summer.toString(), Season.Spring.toString()), "https://backstagetourism.com/anfrage/");
        Attraction attraction9 =
                new Attraction(Activity.Sailing, Location.Black_Forest, 25, true, "5h","Mozartstrasse 6, Freiburg, 79104"
                        ,"11:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
        Attraction attraction10 =
                new Attraction(Activity.Wine_Tasting, Location.Black_Forest, 30, true, "4h","Mozartstrasse 6, Freiburg, 79104"
                        ,"13:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.badische-weinstrasse.de/");

        //TODO: Insert into Cloud Firestore
      //  attractions = Arrays.asList(attraction1,attraction5,attraction8,attraction9);
        attractions = Arrays.asList(attraction1,attraction2,attraction3,attraction4,attraction5,attraction6,attraction7,attraction8,attraction9,attraction10);
    }

    public static List<Location> getTripLocations() {
        List<Location> tripLocations = new ArrayList<>();
        List<Activity> tripActivities = Trip.getInstance().getActivities();
        // If the trip activity is in the list, then add the location of the attraction
        for (Activity activity: tripActivities) {
            for(Attraction attraction: attractions) {
                Location location = attraction.getLocation();
                if(attraction.getActivity().equals(activity) && !tripLocations.contains(location)) {
                    tripLocations.add(location);
                }
            }
        }
        return tripLocations;
    }

    public static ArrayList<String> getAllActivities() {
        ArrayList<String> activities = new ArrayList<>();
        for (Attraction a: attractions) {
            if(!activities.contains(a)) {
                activities.add(a.getActivity().toString());
            }
        }
        return activities;
    }

    public static ArrayList<Activity> getActivitiesForLocation(Location location) {
        ArrayList<Activity> locationActivities = new ArrayList<>();
        ArrayList<Attraction> locationAttractions = getAttractionsForLocation(location);
        for(Attraction locationAttraction : locationAttractions) {
            Activity locationActivity = locationAttraction.getActivity();
            if(!locationActivities.contains(locationActivity)) {
                locationActivities.add(locationActivity);
            }
        }
        return locationActivities;
    }

    public static ArrayList<Attraction> getAttractionsForLocation(Location location) {
        ArrayList<Attraction> tripAttractions = new ArrayList<>();
        for(Attraction attraction: attractions) {
            if(attraction.getLocation() == location) {
                if(!tripAttractions.contains(attraction)) {
                    tripAttractions.add(attraction);
                }
            }
        }
        return tripAttractions;
    }

    public static List<Attraction> getAllAttractions() {
        return attractions;
    }
}
