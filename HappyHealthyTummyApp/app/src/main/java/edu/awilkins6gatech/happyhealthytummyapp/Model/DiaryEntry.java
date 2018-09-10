package edu.awilkins6gatech.happyhealthytummyapp.Model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DiaryEntry {
    private String fileUri;
    private int calories;
    private long timestamp;
    private String title;
    private String description;

    public DiaryEntry (String fileUri, int calories) {
        fileUri = fileUri;
        calories = calories;
        timestamp = System.currentTimeMillis();
    }

    public DiaryEntry() {
        timestamp = System.currentTimeMillis();
        fileUri = Long.toString(timestamp);
        calories = -1;
    }

    public int getCalories() {
        return calories;
    }

    public String getFileUri() {
        return fileUri;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getTimestamp() {
        return  timestamp;
    }
}
