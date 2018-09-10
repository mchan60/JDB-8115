package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import edu.awilkins6gatech.happyhealthytummyapp.R;

public class AddEntryActivity extends AppCompatActivity {

    Button buttonAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_add_entry);
    }

//    protected static DiaryEntry createEntry(DiaryEntry entry) {
//        ObjectMapper mapper = new ObjectMapper();
//        File directory = new File("Entries");
//        File jsonFileOutput = new File("Entries/" + Long.toString(entry.getTimestamp()) + ".json");
//        try {
//            if (!directory.exists()) directory.mkdir();
//            jsonFileOutput.createNewFile();
//            mapper.writeValue(jsonFileOutput, entry);
//            return entry;
//        } catch (Exception e) {
//            System.out.println("A writing error occurred: \n" + e.getMessage()); //TODO: send to log file
//        }
//        return null;
//    }
//
//    protected static DiaryEntry readEntry(String name) {
//        ObjectMapper mapper = new ObjectMapper();
//        DiaryEntry entry = new DiaryEntry();
//        File jsonFile = new File("Entries/" + name + ".json");
//        try {
//            entry = mapper.readValue(jsonFile, DiaryEntry.class);
//        }catch (Exception e) {
//            System.out.println("A reading error occurred: \n" + e.getMessage()); //TODO: send to log file
//        }
//        return entry;
//    }
//
//    protected static DiaryEntry deleteEntry(String name) {
//        ObjectMapper mapper = new ObjectMapper();
//        DiaryEntry entry = new DiaryEntry();
//        File jsonFile = new File("Entries/" + name + ".json");
//        try {
//            entry = mapper.readValue(jsonFile, DiaryEntry.class);
//            jsonFile.delete();
//        }catch (Exception e) {
//            System.out.println("A reading error occurred: \n" + e.getMessage()); //TODO: send to log file
//        }
//        return entry;
//    }
}
