package edu.awilkins6gatech.happyhealthytummyapp.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiaryEntry {
    private Uri fileUri;
    private int calories;
    private String timestamp;
    private String title;
    private String description;
    private boolean happy;
    //private ArrayList
    //private Bitmap

    public DiaryEntry (Uri fileUri, int calories, String timestamp, String title, String description, boolean happy) {
        this.fileUri = fileUri;
        this.calories = calories;
        //timestamp = System.currentTimeMillis();
        this.timestamp = timestamp;
        this.title = title;
        this.description = description;
        this.happy = happy;
    }

    public DiaryEntry() {
//        timestamp = System.currentTimeMillis();
//        fileUri = Uri.parse(Long.toString(timestamp));
//        calories = -1;
        this(null, 0, "0", "N/A", "N/A", false);
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
    public int getCalories() {
        return calories;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public Uri getFileUri() {
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

    public void setHappy(boolean happy) {
        this.happy = happy;
    }

    public boolean getHappy() {
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
        File fileDir = new File("Entries/");
        File[] diaryEntries = fileDir.listFiles();
        List<DiaryEntry> output = new ArrayList<>();
        for (File file : diaryEntries) {
            output.add(readEntry(file.getName()));
            System.out.println(file.getName());
        }
        return output;
//        if (diaryEntries != null) {
//            ArrayList<File> diaryEntriesList = new ArrayList<>(Arrays.asList(diaryEntries));
//            return diaryEntriesList;
//        } else {
//            return null;
//        }
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
}
