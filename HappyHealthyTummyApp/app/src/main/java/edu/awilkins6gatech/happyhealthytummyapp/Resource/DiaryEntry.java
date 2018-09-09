package edu.awilkins6gatech.happyhealthytummyapp.Resource;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DiaryEntry {
    public String FileUri;
    public int Calories;
    public long Timestamp;

    public DiaryEntry (String fileUri, int calories) {
        FileUri = fileUri;
        Calories = calories;
        Timestamp = System.currentTimeMillis();
    }

    public DiaryEntry() {
        Timestamp = System.currentTimeMillis();
        FileUri = Long.toString(Timestamp);
        Calories = -1;
    }
}
