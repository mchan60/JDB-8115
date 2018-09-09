package edu.awilkins6gatech.happyhealthytummyapp.Resource;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Alex Wilkins on 9/7/18.
 * Last edited by Joshua Perry on 9/9/18.
 *
 *
 * Information Holder - represents a diary entry
 *
 * We are passing this object in a bundle between intents, so we implement
 * the Parcelable interface.
 *
 */
public class DiaryEntry {
    public String FileUri;
    private int Calories;
    private String Title;
    private String Description;
    public long Timestamp;

    public String getTitle() {return Title;}
    public void setTitle(String title) {Title = title; }

    public String getDescription() {return Description;}
    public void setDescription(String description) {Description = description;}

    public int getCalories() {return Calories;}
    public void setCalories(int calories) {Calories = calories;}

    public DiaryEntry (String fileUri, int calories, String title, String description) {
        FileUri = fileUri;
        Calories = calories;
        Title = title;
        Description = description;
        Timestamp = System.currentTimeMillis();
    }

    public DiaryEntry() {
        Timestamp = System.currentTimeMillis();
        FileUri = Long.toString(Timestamp);
        Calories = -1;
        Title = "No Title";
        Description = "No description";
    }
}
