package com.example.telegram.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

@Service
public class CommonUtility {

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getProjectProperties(String propertyName) {
        ResourceBundle bundle = ResourceBundle.getBundle("project");
        return bundle.getString(propertyName);
    }

    public static String convertUTCDateToITC(String date) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("HH:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = sdf.parse(date);
        Date local = new Date(utcDate.getTime() );
        sdf = new SimpleDateFormat("HH:mm a");
        return sdf.format(local);
    }
    public static String formateTime(String time)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat((time.length() == 2 ? "mm" : (time.length()==3 ? "Hmm" : "HHmm")));
        Date dt = sdf.parse(time);
        sdf = new SimpleDateFormat("HH:mm aa");
        return sdf.format(dt);
    }
}
