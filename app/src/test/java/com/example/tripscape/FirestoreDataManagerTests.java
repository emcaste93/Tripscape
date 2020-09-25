package com.example.tripscape;

import com.example.tripscape.data.FirestoreDataManager;
import com.example.tripscape.model.Attraction;
import com.example.tripscape.model.Enums;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class FirestoreDataManagerTests {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

     @Test
    public void isAttractionCompatibleWithTripStartDate_ShallReturnTrue() {
        /** POSIVE CASE */
        List<String> seasons = asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(), Enums.Season.Autumn.toString());
        Attraction a = new Attraction("title", Enums.Activity.Skiing, Enums.Location.Berlin, 20,false,"20h",
                "Munich centre", "07:00", seasons, "link");

        boolean res = false;
        String dateInString = "10-05-2020";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
            res = FirestoreDataManager.isAttractionCompatibleWithTripStartDate(a, date);
        } catch (ParseException e) {
            //handle exception if date is not in "dd-MMM-yyyy" format
        }

        assertEquals(true, res);
    }

    @Test
    public void isAttractionCompatibleWithTripStartDate_ShallReturnFalse() {
        /** POSIVE CASE */
        List<String> seasons = asList(Enums.Season.Summer.toString(), Enums.Season.Spring.toString(), Enums.Season.Autumn.toString());
        Attraction a = new Attraction("title", Enums.Activity.Skiing, Enums.Location.Berlin, 20,false,"20h",
                "Munich centre", "07:00", seasons, "link");

        boolean res = false;
        String dateInString = "10-01-2020";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
            res = FirestoreDataManager.isAttractionCompatibleWithTripStartDate(a, date);
        } catch (ParseException e) {
            //handle exception if date is not in "dd-MMM-yyyy" format
        }

        assertEquals(true, res);
    }
}
