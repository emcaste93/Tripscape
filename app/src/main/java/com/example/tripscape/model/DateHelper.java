package com.example.tripscape.model;

import com.example.tripscape.model.Enums.TripDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateHelper {

    public static ArrayList<TripDay> getDayListFromDates(Date start, Date end) {
        ArrayList<TripDay> res = new ArrayList<>();
        TripDay endDay = getTripWeekDay(end);
        TripDay auxDay = getTripWeekDay(start);

        if(start.equals(end)) {
            res.add(auxDay);
            return res;
        }
        else if(auxDay.equals(endDay)) {
            auxDay = getNextTripDay(auxDay);
        }

        while(!auxDay.equals(endDay)) {
            res.add(auxDay);
            auxDay = getNextTripDay(auxDay);
        }

        res.add(auxDay);
        return res;
    }

    public static TripDay getNextTripDay(TripDay day) {
        switch (day) {
            case Monday:
                return TripDay.Tuesday;
            case Tuesday:
                return TripDay.Wednesday;
            case Wednesday:
                return TripDay.Thursday;
            case Thursday:
                return TripDay.Friday;
            case Friday:
                return TripDay.Saturday;
            case Saturday:
                return TripDay.Sunday;
            case Sunday:
                return TripDay.Monday;
        }
        return day;
    }

    public static TripDay getTripWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        TripDay res;

        switch (day) {
            case 1:
                res = TripDay.Sunday;
                break;
            case 2:
                res = TripDay.Monday;
                break;
            case 3:
                res = TripDay.Tuesday;
                break;
            case 4:
                res = TripDay.Wednesday;
                break;
            case 5:
                res = TripDay.Thursday;
                break;
            case 6:
                res = TripDay.Friday;
                break;
            default:
                res = TripDay.Saturday;
                break;
        }
        return res;
    }

    public static boolean containsListAnyDayFromList(List<TripDay> dayList, List<TripDay> attractionList) {
        if(dayList == null) {
            return false;
        }

        for(TripDay day: dayList) {
            if(attractionList.contains(day)) {
                return true;
            }
        }
        return false;
    }
}
