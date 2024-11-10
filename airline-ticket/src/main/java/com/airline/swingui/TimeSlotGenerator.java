package com.airline.swingui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeSlotGenerator {

    public static String[] generateTimeSlots(String startTime, String endTime, int intervalHours) {
        ArrayList<String> timeSlots = new ArrayList<>();
        timeSlots.add("Select Time"); // Default option

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        try {
            start.setTime(sdf.parse(startTime));
            end.setTime(sdf.parse(endTime));
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Invalid Time"};
        }

        while (start.before(end) || start.equals(end)) {
            timeSlots.add(sdf.format(start.getTime()));
            start.add(Calendar.HOUR_OF_DAY, intervalHours);
        }

        return timeSlots.toArray(new String[0]);
    }
}

