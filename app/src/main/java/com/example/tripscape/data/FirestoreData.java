package com.example.tripscape.data;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripscape.R;
import com.example.tripscape.TripApplication;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.DateHelper;
import com.example.tripscape.model.Enums;
import com.example.tripscape.model.Trip;
import com.example.tripscape.presentation.EnterDataFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import static com.example.tripscape.model.Enums.*;
import static java.util.Arrays.asList;

public class FirestoreData extends AppCompatActivity {
    private AttractionFirestore attractions;
    private static List<Attraction> attractionList;
    public FirestoreData() {
       attractionList = new ArrayList<>();
       attractions = new AttractionFirestore();
       Query query = FirebaseFirestore.getInstance()
               .collection("attractions")
               .limit(50);
       attractions.getAttractions(new FirestoreDataCallback(){
           public void addAttraction(Attraction attraction) {
               attractionList.add(attraction);
           }
       });
    //   generateAttractionsData();
    }
        public static void generateAttractionsData() {
        Attraction attraction1 =
                new Attraction("Hirschberg", Activity.Hiking, Location.Munich, 20, true, "6h","Bahnhofplatz, Munich, 80335"
                        ,"08:30", asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString()), "https://chiemsee-sailingcenter.de");
        Attraction attraction2 =
                new Attraction("Chiemsee", Activity.Sailing, Enums.Location.Munich, 30, true, "6h","Bahnhofplatz, Munich, 80335"
                        ,"10:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://chiemsee-sailingcenter.de");
        Attraction attraction3 =
                new Attraction("Munich",Activity.Sightseeing, Enums.Location.Munich, 30, false, "3h","Marienplatz, Munich, 80331"
                        ,"10:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://chiemsee-sailingcenter.de");
        Attraction attraction4 =
                new Attraction("Hirschberg", Activity.Skiing, Enums.Location.Munich, 50, true, "8H","Bahnhofplatz, Munich, 80335"
                        ,"08:00", Arrays.asList(Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://https://www.skiwelt.at/en/individual-tariff.html");
        Attraction attraction5 =
                new Attraction("Hamburg", Activity.Canoeing, Location.Hamburg, 10, false, "2h","Rathausmarkt, Hamburg, 20095"
                        ,"11:00", asList(Season.Summer.toString(), Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
        Attraction attraction6 =
                new Attraction("Hamburg", Activity.Sailing, Enums.Location.Hamburg, 10, false, "4h","Rathausmarkt, Hamburg, 20095"
                        ,"12:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
        Attraction attraction7 =
                new Attraction("Bus-tour", Activity.Sightseeing, Enums.Location.Berlin, 15, true, "3h","Alexanderplatz, Berlin, 10178"
                        ,"16:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://backstagetourism.com/anfrage/");
        Attraction attraction8 =
                new Attraction("Canoeing", Activity.Canoeing, Location.Berlin, 12, false, "3h","Alexanderplatz, Berlin, 10178"
                        ,"15:00", asList(Season.Summer.toString(), Season.Spring.toString()), "https://backstagetourism.com/anfrage/");
        Attraction attraction9 =
                new Attraction("Sailing", Activity.Sailing, Location.Black_Forest, 25, true, "5h","Mozartstrasse 6, Freiburg, 79104"
                        ,"11:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
        Attraction attraction10 =
                new Attraction("Wine-tour", Activity.Wine_Tasting, Location.Black_Forest, 30, true, "4h","Mozartstrasse 6, Freiburg, 79104"
                        ,"13:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.badische-weinstrasse.de/");

        attractionList = Arrays.asList(attraction1,attraction2,attraction3,attraction4,attraction5,attraction6,attraction7,attraction8,attraction9,attraction10);
    }

    /** Retunrs a list of every Location compatible with the trip search(Activities and Season) */
    public static List<Location> getTripLocations() {
        List<Location> tripLocations = new ArrayList<>();
        List<Activity> tripActivities = Trip.getInstance().getDesiredActivities();
        // If the trip activity is in the list, then add the location of the attraction
        for (Activity activity: tripActivities) {
            for(Attraction attraction: attractionList) {
                Location location = attraction.getLocation();
                if(attraction.getActivity().equals(activity) && isAttractionCompatibleWithTripStartDate(attraction,Trip.getInstance().getStartDate())
                        && !tripLocations.contains(location)) {
                    tripLocations.add(location);
                }
            }
        }
        return tripLocations;
    }
    
    public static boolean isAttractionCompatibleWithTripStartDate(Attraction a, Date startDate) {
        String season = getSeasonFromDate(startDate);
        boolean res = a.getSeasonsAvailable().contains(season);
        return res;
    }

    public static String getSeasonFromDate(Date d) {
        String res = Season.Summer.toString();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        int month =  calendar.get(Calendar.MONTH) + 1;
        switch (month) {
            case 1:
            case 2:
            case 12:
                res = Season.Winter.toString();
                break;
            case 3:
            case 4:
            case 5:
                res = Season.Spring.toString();
                break;
            case 6:
            case 7:
            case 8:
                res = Season.Summer.toString();
                break;
            case 9:
            case 10:
            case 11:
                res = Season.Autumn.toString();
                break;
        }
        return res;
    }

    public static ArrayList<String> getAllActivities() {
        ArrayList<String> activities = new ArrayList<>();
        for (Attraction a: attractionList) {
            if(!activities.contains(a)) {
                activities.add(a.getActivity().toString());
            }
        }
        return activities;
    }

    public static ArrayList<Activity> getActivitiesForLocation(Location location, Date startDate) {
        ArrayList<Activity> locationActivities = new ArrayList<>();
        ArrayList<Attraction> locationAttractions = getAttractionsForLocation(location, startDate);
        for(Attraction locationAttraction : locationAttractions) {
            Activity locationActivity = locationAttraction.getActivity();
            if(!locationActivities.contains(locationActivity)) {
                locationActivities.add(locationActivity);
            }
        }
        return locationActivities;
    }

    public static ArrayList<Attraction> getAttractionsForLocation(Location location, Date startDate) {
        ArrayList<Attraction> tripAttractions = new ArrayList<>();
        for(Attraction attraction: attractionList) {
            if(attraction.getLocation() == location && isAttractionCompatibleWithTripStartDate(attraction, startDate)
                && !tripAttractions.contains(attraction)) {
                    tripAttractions.add(attraction);
            }
        }
        return tripAttractions;
    }

    public static ArrayList<Attraction> getAttractionsForLocation(Location location, Date startDate, Date endDate, int maxBudget) {
        List<TripDay> days = DateHelper.getDayListFromDates(startDate, endDate);
        int budget = 0;
        ArrayList<Attraction> tripAttractions = new ArrayList<>();
        for(Attraction attraction: attractionList) {
            if(attraction.getLocation() == location && isAttractionCompatibleWithTripStartDate(attraction, startDate)
                    && !tripAttractions.contains(attraction) && DateHelper.containsListAnyDayFromList(days, attraction.getTripDays()) ) {
                budget += attraction.getPrice() * Trip.getInstance().getNumPersons();
                if(budget <= maxBudget) {
                    tripAttractions.add(attraction);
                }
            }
        }
        return tripAttractions;
    }

    public static List<Attraction> getAllAttractions() {
        return attractionList;
    }

    public static int getDrawableFromLocation(Location location) {
        switch (location) {
            case Munich:
                return R.drawable.munich_3;
            case Berlin:
                return R.drawable.berlin;
            case Hamburg:
                return R.drawable.hamburg;
            case Black_Forest:
                return R.drawable.black_forest;
            case Cologne:
                return R.drawable.cologne;
            case Nuremberg:
                return R.drawable.nuremberg;
            default:
                return  R.drawable.munich_4;
        }
    }

    /** Helper method used to display the images for each activity */
    public static int getDrawableFromActivity(Activity activity) {
        switch (activity) {
            case Hiking:
                return R.drawable.mountain;
            case Skiing:
                return R.drawable.skiing;
            case Wine_Tasting:
                return R.drawable.wine;
            case Canoeing:
                return R.drawable.kayak;
            case Sailing:
                return R.drawable.boat;
            case Sightseeing:
                return R.drawable.camera;
            case Flying:
                return R.drawable.plane;
            case Jetski:
                return R.drawable.jetski;
            default:
                return  R.drawable.munich_4;
        }
    }
}
