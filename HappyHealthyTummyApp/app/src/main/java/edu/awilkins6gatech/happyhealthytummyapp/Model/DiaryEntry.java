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
    //private ArrayList
    //private Bitmap

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
        //timestamp = System.currentTimeMillis();
        this.timestamp = timestamp;
        this.title = title;
        this.description = description;
        this.happy = happy;
    }

    public String getEntryID() { return entryID; }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getCalories() {
        return String.valueOf(calories);
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

//    private DiaryEntry(Parcel in) {
//        fileUri = in.readString();
//        calories = in.readInt();
//        timestamp = in.readString();
//        title = in.readString();
//        description = in.readString();
//        happy = in.readInt();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    /* *************************
//       If you add new instance vars to Student, you will need to add them to the write
//     */
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(fileUri);
//        dest.writeInt(calories);
//        dest.writeString(timestamp);
//        dest.writeString(title);
//        dest.writeString(description);
//        dest.writeInt(happy);
//    }
//
//    public static final Parcelable.Creator<DiaryEntry> CREATOR
//            = new Parcelable.Creator<DiaryEntry>() {
//        public DiaryEntry createFromParcel(Parcel in) {
//            return new DiaryEntry(in);
//        }
//
//        public DiaryEntry[] newArray(int size) {
//            return new DiaryEntry[size];
//        }
//    };
}
