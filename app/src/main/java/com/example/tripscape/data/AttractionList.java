package com.example.tripscape.data;

import com.example.tripscape.model.Attraction;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tripscape.model.Enums.*;
import static java.util.Arrays.asList;

public class AttractionList {
    protected List<Attraction> attractionList;
    GeoPoint coordinatesHirschberg, coordinatesChiemsee, coordinatesMunich, coordinatesZugspitze, coordinatesSoell, coordinatesAlterSee, coordinatesBerlin, coordinatesBlackForest,
        coordinatesNuremberg, coordinatesCologne, coordinatesRavenaGorge, coordinatesFeldBerg;
    public AttractionList() {
        attractionList = new ArrayList<>();
        coordinatesHirschberg = new GeoPoint(47.660783,11.696084);
        coordinatesChiemsee = new GeoPoint(47.860498, 12.365232);
        coordinatesMunich = new GeoPoint(48.137565, 11.575513);
        coordinatesZugspitze = new GeoPoint(47.421675, 10.985065);
        coordinatesSoell = new GeoPoint(47.504006, 12.192491);
        coordinatesAlterSee = new GeoPoint(53.562502, 10.010320);
        coordinatesBerlin = new GeoPoint(52.522053, 13.413381);
        coordinatesBlackForest = new GeoPoint(47.999994, 7.859852);
        coordinatesNuremberg = new GeoPoint(49.455864, 11.075302);
        coordinatesCologne = new GeoPoint(50.937945, 6.959345);
        coordinatesRavenaGorge = new GeoPoint(47.920685, 8.086533);
        coordinatesFeldBerg = new GeoPoint(47.875469, 8.005767);
        addSampleData();
    }

    public void addAttraction(Attraction attraction) {
        attractionList.add(attraction);
    }

    private void addSampleData() {
        addAttraction(new Attraction("Hirschberg", Activity.Hiking, Location.Munich, 20, true, "6h","Bahnhofplatz, Munich, 80335"
                ,"08:30", asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString()), "https://chiemsee-sailingcenter.de", asList(TripDay.Monday, TripDay.Wednesday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesHirschberg, 5));
        addAttraction(new Attraction("Chiemsee", Activity.Sailing, Location.Munich, 30, true, "6h","Bahnhofplatz, Munich, 80335,"
                ,"10:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString()), "https://chiemsee-sailingcenter.de", asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Saturday, TripDay.Sunday),coordinatesChiemsee, 5.0));
        addAttraction(new Attraction("Munich", Activity.Sightseeing, Location.Munich, 30, false, "3h","Marienplatz, Munich, 80331"
                ,"10:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString(), Season.Winter.toString()), "https://chiemsee-sailingcenter.de", asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesMunich, 4.2));
        addAttraction(new Attraction("Hirschberg", Activity.Skiing, Location.Munich, 50, true, "8H","Bahnhofplatz, Munich, 80335"
                ,"08:00", Arrays.asList(Season.Autumn.toString(), Season.Winter.toString()), "https://https://www.skiwelt.at/en/individual-tariff.html",asList(TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesHirschberg, 3.1));
        addAttraction(new Attraction("Zugspitze", Activity.Skiing, Location.Munich, 75, true, "8H","Bahnhofplatz, Munich, 80335"
                ,"08:00", Arrays.asList(Season.Autumn.toString(), Season.Winter.toString()), "https://www.skiresort.info/ski-resort/zugspitze/",asList(TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesZugspitze, 1.4));
        addAttraction(new Attraction("Söll", Activity.Skiing, Location.Munich, 70, true, "10H","Bahnhofplatz, Munich, 80335"
                ,"08:00", Arrays.asList(Season.Autumn.toString(), Season.Winter.toString(), Season.Spring.toString()), "https://https://www.skiwelt.at/en/individual-tariff.html",asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesSoell, 40.1));
        addAttraction(new Attraction("Alstersee", Activity.Canoeing, Location.Hamburg, 10, true, "2h","Rathausmarkt, Hamburg, 20095"
                ,"11:00", asList(Season.Summer.toString(), Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/",asList(TripDay.Monday, TripDay.Tuesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesAlterSee, 49.0));
        addAttraction(new Attraction("Alstersee", Activity.Sailing, Location.Hamburg, 10, true, "4h","Rathausmarkt, Hamburg, 20095"
                ,"12:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/",asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesAlterSee, 49.1));
        addAttraction(new Attraction("Bus-tour", Activity.Sightseeing, Location.Berlin, 15, true, "3h","Alexanderplatz, Berlin, 10178"
                ,"16:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString()), "https://backstagetourism.com/anfrage/",asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesBerlin, 31.1));
        addAttraction(new Attraction("Canoeing", Activity.Canoeing, Location.Berlin, 12, true, "3h","Alexanderplatz, Berlin, 10178"
                ,"15:00", asList(Season.Summer.toString(), Season.Spring.toString()), "https://backstagetourism.com/anfrage/",asList(TripDay.Monday, TripDay.Wednesday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesBerlin, 30));
        addAttraction(new Attraction("Sailing", Activity.Sailing, Location.Black_Forest, 25, true, "5h","Mozartstrasse, Freiburg, 79104"
                ,"11:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/",asList(TripDay.Tuesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesBlackForest, 2));
        addAttraction(new Attraction("Wine-tour", Activity.Wine_Tasting, Location.Black_Forest, 30, true, "4h","Mozartstrasse, Freiburg, 79104"
                ,"13:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString()), "https://www.badische-weinstrasse.de/",asList(TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesBlackForest, 3));
        addAttraction(new Attraction("Feldberg", Activity.Skiing, Location.Black_Forest, 60, true, "4h","Mozartstrasse, Freiburg, 79104"
                ,"13:00", Arrays.asList(Season.Winter.toString(), Season.Autumn.toString()), "https://www.skiresort.info/ski-resort/feldberg-seebuckgrafenmattfahl/",asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesFeldBerg, 1));
        addAttraction(new Attraction("Ravenna Gorge", Activity.Hiking, Location.Black_Forest, 20, true, "4h","Mozartstrasse, Freiburg, 79104"
                ,"13:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString()), "https://www.alltrails.com/trail/germany/baden-wurttemberg/ravenna-gorge-trail",asList(TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesRavenaGorge, 3));
        addAttraction(new Attraction("Walking tour", Activity.Sightseeing, Location.Nuremberg, 10, true, "2h","Füll, Nuremberg, 90403"
                ,"13:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString()), "https://www.alltrails.com/trail/germany/baden-wurttemberg/ravenna-gorge-trail",asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesNuremberg, -1.0));
        addAttraction(new Attraction("Wingly", Activity.Flying, Location.Cologne, 150, true, "4h","Marsplatz 6, Köln, 50667"
                ,"12:00", Arrays.asList(Season.Summer.toString(), Season.Spring.toString(), Season.Autumn.toString()), "https://www.getyourguide.de/activity/cologne-l19/cologne-sightseeing-flight-in-a-private-plane-t214839?utm_force=0",asList(TripDay.Monday, TripDay.Tuesday, TripDay.Wednesday, TripDay.Thursday, TripDay.Friday, TripDay.Saturday, TripDay.Sunday), coordinatesCologne, -3.912));
    }

    public int getSize() {
        return attractionList.size();
    }

    public Attraction getElementAt(int id) {
        return attractionList.get(id);
    }
}
