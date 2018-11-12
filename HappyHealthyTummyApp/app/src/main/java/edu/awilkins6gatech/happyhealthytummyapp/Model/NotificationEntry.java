package edu.awilkins6gatech.happyhealthytummyapp.Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Matt on 11/3/2018.
 */

public class NotificationEntry implements Serializable{

    private int id;
    private String time;
    private String meal;

    public NotificationEntry() {
        id = 0;
        time = "";
        meal = "";
    }

    public NotificationEntry(int id, String time, String meal) {
        this();
        this.id = id;
        this.time = time;
        this.meal = meal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        String[] splitTime = getTime().split(":");
        int hour = Integer.parseInt(splitTime[0]);
        String ampm = (hour < 12) ? "AM" : "PM";
        hour = (hour % 12 == 0) ? 12 : hour % 12;
        return String.format("%02d:%s %s | %s", hour, splitTime[1], ampm, meal);
    }
}
