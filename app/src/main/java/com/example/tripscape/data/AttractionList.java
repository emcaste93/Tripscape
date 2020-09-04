package com.example.tripscape.data;

import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class AttractionList {
    protected List<Attraction> attractionList;
    public AttractionList() {
        attractionList = new ArrayList<Attraction>();
        addSampleData();
    }

    public void addAttraction(Attraction attraction) {
        attractionList.add(attraction);
    }

    private void addSampleData() {
        addAttraction(new Attraction("Hirschberg", Enums.Activity.Hiking, Enums.Location.Munich, 20, true, "6h","Bahnhofplatz, Munich, 80335"
                ,"08:30", asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(), Enums.Season.Autumn.toString()), "https://chiemsee-sailingcenter.de"));
        addAttraction(new Attraction("Chiemsee", Enums.Activity.Sailing, Enums.Location.Munich, 30, true, "6h","Bahnhofplatz, Munich, 80335"
                ,"10:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://chiemsee-sailingcenter.de"));
        addAttraction(new Attraction("Munich", Enums.Activity.Sightseeing, Enums.Location.Munich, 30, false, "3h","Marienplatz, Munich, 80331"
                ,"10:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://chiemsee-sailingcenter.de"));
        addAttraction(new Attraction("Hirschberg", Enums.Activity.Skiing, Enums.Location.Munich, 50, true, "8H","Bahnhofplatz, Munich, 80335"
                ,"08:00", Arrays.asList(Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://https://www.skiwelt.at/en/individual-tariff.html"));
        addAttraction(new Attraction("Zugspitze", Enums.Activity.Skiing, Enums.Location.Munich, 75, true, "8H","Bahnhofplatz, Munich, 80335"
                ,"08:00", Arrays.asList(Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://www.skiresort.info/ski-resort/zugspitze/"));
        addAttraction(new Attraction("Söll", Enums.Activity.Skiing, Enums.Location.Munich, 70, true, "10H","Bahnhofplatz, Munich, 80335"
                ,"08:00", Arrays.asList(Enums.Season.Autumn.toString(),Enums.Season.Winter.toString(), Enums.Season.Spring.toString()), "https://https://www.skiwelt.at/en/individual-tariff.html"));
        addAttraction(new Attraction("Hamburg", Enums.Activity.Canoeing, Enums.Location.Hamburg, 10, false, "2h","Rathausmarkt, Hamburg, 20095"
                ,"11:00", asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/"));
        addAttraction(new Attraction("Hamburg", Enums.Activity.Sailing, Enums.Location.Hamburg, 10, false, "4h","Rathausmarkt, Hamburg, 20095"
                ,"12:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/"));
        addAttraction(new Attraction("Bus-tour", Enums.Activity.Sightseeing, Enums.Location.Berlin, 15, true, "3h","Alexanderplatz, Berlin, 10178"
                ,"16:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://backstagetourism.com/anfrage/"));
        addAttraction(new Attraction("Canoeing", Enums.Activity.Canoeing, Enums.Location.Berlin, 12, false, "3h","Alexanderplatz, Berlin, 10178"
                ,"15:00", asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://backstagetourism.com/anfrage/"));
        addAttraction(new Attraction("Sailing", Enums.Activity.Sailing, Enums.Location.Black_Forest, 25, true, "5h","Mozartstrasse, Freiburg, 79104"
                ,"11:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/"));
        addAttraction(new Attraction("Wine-tour", Enums.Activity.Wine_Tasting, Enums.Location.Black_Forest, 30, true, "4h","Mozartstrasse, Freiburg, 79104"
                ,"13:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.badische-weinstrasse.de/"));
        addAttraction(new Attraction("Feldberg", Enums.Activity.Skiing, Enums.Location.Black_Forest, 60, false, "4h","Mozartstrasse, Freiburg, 79104"
                ,"13:00", Arrays.asList(Enums.Season.Winter.toString(),Enums.Season.Autumn.toString()), "https://www.skiresort.info/ski-resort/feldberg-seebuckgrafenmattfahl/"));
        addAttraction(new Attraction("Ravenna Gorge", Enums.Activity.Hiking, Enums.Location.Black_Forest, 20, true, "4h","Mozartstrasse, Freiburg, 79104"
                ,"13:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.alltrails.com/trail/germany/baden-wurttemberg/ravenna-gorge-trail"));
        addAttraction(new Attraction("Walking tour", Enums.Activity.Sightseeing, Enums.Location.Nuremberg, 10, false, "2h","Füll, Nuremberg, 90403"
                ,"13:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.alltrails.com/trail/germany/baden-wurttemberg/ravenna-gorge-trail"));
        addAttraction(new Attraction("Wingly", Enums.Activity.Flying, Enums.Location.Cologne, 150, false, "4h","Marsplatz 6, Köln, 50667"
                ,"12:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.getyourguide.de/activity/cologne-l19/cologne-sightseeing-flight-in-a-private-plane-t214839?utm_force=0"));

    }

    public int getSize() {
        return attractionList.size();
    }

    public Attraction getElementAt(int id) {
        return attractionList.get(id);
    }
}
