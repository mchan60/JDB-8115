package edu.awilkins6gatech.happyhealthytummyapp.Model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiaryEntry implements Serializable {
    private String entryID;
    private String fileUri;
    private int calories;
    private String timestamp;
    private String title;
    private String description;
    private int happy;
    private static final String ENTRY_DIRECTORY_NAME = "Entries";
    private String month;
    private int monthNumber;
    private String day;
    private String dayNumber;
    private String year;
    private String hour;
    private String minutes;
    private boolean am;
    private boolean pm;
    private String formattedHour;



    public DiaryEntry() {
        this.entryID = "0";
        this.fileUri = null;
        this.calories = 0;
        this.timestamp = "0";
        this.title = "N/A";
        this.description = "N/A";
        this.happy = 0;
    }

    public DiaryEntry (String fileUri, int calories, String timestamp, String title, String description, int happy) {
        this();
        this.entryID = timestamp;
        this.fileUri = fileUri;
        this.calories = calories;
        this.timestamp = timestamp;
        this.title = title;
        this.description = description;
        this.happy = happy;
    }

    public String getEntryID() { return entryID; }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCalories() {
        return calories;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return  timestamp;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getHappy() {
        return happy;
    }

    public DiaryEntry createEntry(DiaryEntry entry) {
        ObjectMapper mapper = new ObjectMapper();
        File directory = new File("Entries");
        File jsonFileOutput = new File("Entries/" + (entry.getTimestamp()) + ".json");
        try {
            if (!directory.exists()) directory.mkdir();
            jsonFileOutput.createNewFile();
            mapper.writeValue(jsonFileOutput, entry);
            return entry;
        } catch (Exception e) {
            System.out.println("A writing error occurred: \n" + e.getMessage()); //TODO: send to log file
        }
        return null;
    }

    public static List<DiaryEntry> getDiaryEntries() {
        File fileDir = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                ENTRY_DIRECTORY_NAME);
        File[] diaryEntries = fileDir.listFiles();
        List<DiaryEntry> output = new ArrayList<>();
        for (File file : diaryEntries) {
            output.add(readEntry(file.getName()));
                System.out.println(file.getName());
        }
        return output;
    }

    public static DiaryEntry readEntry(String timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        DiaryEntry entry = new DiaryEntry();
        File jsonFile = new File("Entries/" + timestamp);
        try {
            entry = mapper.readValue(jsonFile, DiaryEntry.class);
        }catch (Exception e) {
            System.out.println("A reading error occurred: \n" + e.getMessage()); //TODO: send to log file
        }
        return entry;
    }

    public DiaryEntry editEntry(DiaryEntry newEntry) {
        ObjectMapper mapper = new ObjectMapper();
        DiaryEntry entry = new DiaryEntry();
        File jsonFile = new File("Entries/" + timestamp + ".json");
        try {
            entry = mapper.readValue(jsonFile, DiaryEntry.class);
            entry = newEntry;
        }catch (Exception e) {
            System.out.println("A reading error occurred: \n" + e.getMessage()); //TODO: send to log file
        }
        return entry;
    }

    public DiaryEntry deleteEntry() {
        ObjectMapper mapper = new ObjectMapper();
        DiaryEntry entry = new DiaryEntry();
        File jsonFile = new File("Entries/" + timestamp + ".json");
        try {
            entry = mapper.readValue(jsonFile, DiaryEntry.class);
            jsonFile.delete();
        }catch (Exception e) {
            System.out.println("A reading error occurred: \n" + e.getMessage()); //TODO: send to log file
        }
        return entry;
    }

    @Override
    public boolean equals(Object entry) {
        boolean isEqual;
        if (entry instanceof DiaryEntry) {
            DiaryEntry diaryEntry = (DiaryEntry) entry;
            isEqual = timestamp.equals(diaryEntry.getTimestamp());
        } else {
            isEqual = false;
        }
        return isEqual;
    }

    public String getYear() {
        year = timestamp.substring(0,4);
        return year;
    }

    public String getMonth() {
        month = timestamp.substring(4,7);
        return month;
    }

    public String getDayNumber() {
        dayNumber = timestamp.substring(7,9);
        dayNumber = dayNumber.replaceAll(" ", "");
        dayNumber = dayNumber.trim();
        return dayNumber;
    }

    public String getDay() {
        if (timestamp != null) {
            day = timestamp.substring(9,13);
        } else {
            day = "Not a Day";
        }
        return day;
    }

    public String getHour() {
        hour = timestamp.substring(14,16);
        return hour;
    }

    public String getMinutes() {
        minutes = timestamp.substring(16,18);
        return minutes;
    }

    public String getFormattedHour(String hour) {
        if (Integer.parseInt(hour) > 12) {
            formattedHour = String.valueOf((Integer.parseInt(hour) - 12));
        } else {
            formattedHour = hour;
        }
        return formattedHour;
    }

    public void setAMorPM() {
        if (Integer.parseInt(hour) > 11 && Integer.parseInt(hour) < 24) {
            pm = true;
        } else {
            am = true;
        }
    }
    public String getFormattedTime() {
        day = getDay();
        month = getMonth();
        dayNumber = getDayNumber();
        year = getYear();
        //String formattedTimeStamp = getFormattedHour(getHour()) +":" +getMinutes();
//        if (am) {
//            formattedTimeStamp = formattedTimeStamp + " " + "AM";
//        } else {
//            formattedTimeStamp = formattedTimeStamp + " " + "PM";
//        }
        return day + ", " + month + " " + dayNumber + ", " + year + " "; // + formattedTimeStamp;
    }

    public int getMonthNumber() {
        month = getMonth();
        int monthNumber = 0;
        switch(month) {
            case "Jan":
                monthNumber = 1;
            case "Feb":
                monthNumber = 2;
            case "Mar":
                monthNumber = 3;
            case "Apr":
                monthNumber = 4;
            case "May":
                monthNumber = 5;
            case "Jun":
                monthNumber = 6;
            case "Jul":
                monthNumber = 7;
            case "Aug":
                monthNumber = 8;
            case "Sep":
                monthNumber = 9;
            case "Oct":
                monthNumber = 10;
            case "Nov":
                monthNumber = 11;
            case "Dec":
                monthNumber = 12;
        }
        return monthNumber;
    }
}
