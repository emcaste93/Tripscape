package com.example.tripscape.data;

import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;

import java.util.Arrays;
import java.util.List;
import static java.util.Arrays.asList;

public class FirestoreData {
    private static List<Attraction> attractions;

    public static void generateAttractionsData() {
        Attraction attraction1 =
                new Attraction(Enums.Activity.Hiking.toString(), Enums.Location.Munich, 20, true, "6h","Bahnhofplatz, Munich, 80335"
                        ,"08:30", asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(), Enums.Season.Autumn.toString()), "https://chiemsee-sailingcenter.de");
       /* Attraction attraction2 =
                new Attraction(Enums.Activity.Sailing.toString(), Enums.Location.Munich, 30, true, "6h","Bahnhofplatz, Munich, 80335"
                        ,"10:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://chiemsee-sailingcenter.de");
        Attraction attraction3 =
                new Attraction(Enums.Activity.Sightseeing.toString(), Enums.Location.Munich, 30, false, "3h","Marienplatz, Munich, 80331"
                        ,"10:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://chiemsee-sailingcenter.de");
        Attraction attraction4 =
                new Attraction(Enums.Activity.Skiing.toString(), Enums.Location.Munich, 50, true, "12j","Bahnhofplatz, Munich, 80335"
                        ,"08:00", Arrays.asList(Enums.Season.Autumn.toString(),Enums.Season.Winter.toString()), "https://https://www.skiwelt.at/en/individual-tariff.html");*/
        Attraction attraction5 =
                new Attraction(Enums.Activity.Canoeing.toString(), Enums.Location.Hamburg, 10, false, "2h","Rathausmarkt, Hamburg, 20095"
                        ,"11:00", asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
        /*Attraction attraction6 =
                new Attraction(Enums.Activity.Sailing.toString(), Enums.Location.Hamburg, 10, false, "4h","Rathausmarkt, Hamburg, 20095"
                        ,"12:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
        Attraction attraction7 =
                new Attraction(Enums.Activity.Sightseeing.toString(), Enums.Location.Berlin, 15, true, "3h","Alexanderplatz, Berlin, 10178"
                        ,"16:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.getyourguide.de/activity/berlin-l17/berlin-hop-on-hop-off-sightseeing-busfahrt-mit-live-guide-t272147?utm_force=0");
        */
        Attraction attraction8 =
                new Attraction(Enums.Activity.Canoeing.toString(), Enums.Location.Berlin, 12, false, "3h","Alexanderplatz, Berlin, 10178"
                        ,"15:00", asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://backstagetourism.com/anfrage/");
        Attraction attraction9 =
                new Attraction(Enums.Activity.Sailing.toString(), Enums.Location.Black_Forest, 25, true, "5h","Mozartstrasse 6, Freiburg, 79104"
                        ,"11:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString()), "https://www.hamburg.com/boating/14055536/on-the-alster-lake/");
      /*  Attraction attraction10 =
                new Attraction(Enums.Activity.Wine_Tasting.toString(), Enums.Location.Black_Forest.toString(), 30, true, "4h","Mozartstrasse 6, Freiburg, 79104"
                        ,"13:00", Arrays.asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(),Enums.Season.Autumn.toString()), "https://www.badische-weinstrasse.de/");*/

        //TODO: Insert into Cloud Firestore
        attractions = Arrays.asList(attraction1,attraction5,attraction8);
      //  attractions = Arrays.asList(attraction1,attraction2,attraction3,attraction4,attraction5,attraction6,attraction7,attraction8,attraction9,attraction10);
    }

    public static List<Attraction> getAllAttractions() {
        return attractions;
    }
}
