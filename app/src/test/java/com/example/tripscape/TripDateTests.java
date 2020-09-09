package com.example.tripscape;

import com.example.tripscape.model.DateHelper;
import com.example.tripscape.model.Enums;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TripDateTests {
    @Test
    public void getTripWeekDay_ShallReturnSunday() {
        Enums.TripDay result = null;
        /** POSIVE CASE */

        String tuesdayString = "06-09-2020"; //Sunday
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date tuesdayDate = formatter.parse(tuesdayString);
            System.out.println(tuesdayDate);
            System.out.println(formatter.format(tuesdayDate));
            result = DateHelper.getTripWeekDay(tuesdayDate);
        } catch (ParseException e) {
            //handle exception if date is not in "dd-MMM-yyyy" format
        }
        assertEquals(Enums.TripDay.Sunday, result);
    }

        @Test
        public void getTripWeekDay_ShallReturnWednesday() {
            Enums.TripDay result = null;
            /** POSIVE CASE */

            String wedString = "07-10-2020"; //Wednesday
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date wedDate = formatter.parse(wedString);
                System.out.println(wedDate);
                System.out.println(formatter.format(wedDate));
                result = DateHelper.getTripWeekDay(wedDate);
            } catch (ParseException e) {
                //handle exception if date is not in "dd-MMM-yyyy" format
            }
            assertEquals(Enums.TripDay.Wednesday, result);
        }

    @Test
    public void getTripWeekDay_ShallReturn_SuMoTu() {
        ArrayList<Enums.TripDay> expectedresultList = new ArrayList<>(Arrays.asList(Enums.TripDay.Sunday, Enums.TripDay.Monday, Enums.TripDay.Tuesday));
        ArrayList<Enums.TripDay> result = null;

        String startDateString = "06-09-2020"; //Sun
        String endDateString = "08-09-2020"; //Tue
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date startDate = formatter.parse(startDateString);
            System.out.println(startDate);
            Date endDate = formatter.parse(endDateString);
            System.out.println(endDate);
            result = DateHelper.getDayListFromDates(startDate, endDate);

        } catch (ParseException e) {
            //handle exception if date is not in "dd-MMM-yyyy" format
        }
        assertEquals(expectedresultList,result);
    }

    @Test
    public void getTripWeekDay_ShallReturn_SuMoTuWedThFrSa() {
        ArrayList<Enums.TripDay> expectedresultList = new ArrayList<>(
                Arrays.asList( Enums.TripDay.Tuesday, Enums.TripDay.Wednesday, Enums.TripDay.Thursday, Enums.TripDay.Friday, Enums.TripDay.Saturday, Enums.TripDay.Sunday, Enums.TripDay.Monday));
        ArrayList<Enums.TripDay> result = null;
        String startDateString = "31-08-2020"; //Mo
        String endDateString = "07-09-2020"; //Mo
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date startDate = formatter.parse(startDateString);
            System.out.println(startDate);
            Date endDate = formatter.parse(endDateString);
            System.out.println(endDate);
            result = DateHelper.getDayListFromDates(startDate, endDate);

        } catch (ParseException e) {
            //handle exception if date is not in "dd-MMM-yyyy" format
        }
        assertEquals(expectedresultList,result);
    }


    @Test
    public void listContainsAnyDayFromSecondList() {
        ArrayList<Enums.TripDay> list1 = new ArrayList<>(
                Arrays.asList( Enums.TripDay.Saturday, Enums.TripDay.Monday));
        ArrayList<Enums.TripDay> list2 = new ArrayList<>(
                Arrays.asList( Enums.TripDay.Tuesday, Enums.TripDay.Wednesday, Enums.TripDay.Thursday, Enums.TripDay.Friday, Enums.TripDay.Saturday, Enums.TripDay.Sunday, Enums.TripDay.Monday));

        boolean result = DateHelper.containsListAnyDayFromList(list1, list2);

        assertEquals(result,true);
    }
}
