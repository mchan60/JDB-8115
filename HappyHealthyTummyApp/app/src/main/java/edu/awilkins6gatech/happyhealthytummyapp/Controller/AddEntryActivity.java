package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.awilkins6gatech.happyhealthytummyapp.R;
import edu.awilkins6gatech.happyhealthytummyapp.Resource.DiaryEntry;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;

public class AddEntryActivity extends AppCompatActivity {

    private TextView titleField;
    private TextView descriptionField;
    private Button addButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        titleField = (TextView) findViewById(R.id.titleField);
        descriptionField = (TextView) findViewById(R.id.descriptionField);
        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.content_main_page);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.content_main_page);
            }
        });
    }

    protected static DiaryEntry createEntry(DiaryEntry entry) {
        ObjectMapper mapper = new ObjectMapper();
        File directory = new File("Entries");
        File jsonFileOutput = new File("Entries/" + Long.toString(entry.Timestamp) + ".json");
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

    protected static DiaryEntry readEntry(String name) {
        ObjectMapper mapper = new ObjectMapper();
        DiaryEntry entry = new DiaryEntry();
        File jsonFile = new File("Entries/" + name + ".json");
        try {
            entry = mapper.readValue(jsonFile, DiaryEntry.class);
        }catch (Exception e) {
            System.out.println("A reading error occurred: \n" + e.getMessage()); //TODO: send to log file
        }
        return entry;
    }

    protected static DiaryEntry deleteEntry(String name) {
        ObjectMapper mapper = new ObjectMapper();
        DiaryEntry entry = new DiaryEntry();
        File jsonFile = new File("Entries/" + name + ".json");
        try {
            entry = mapper.readValue(jsonFile, DiaryEntry.class);
            jsonFile.delete();
        }catch (Exception e) {
            System.out.println("A reading error occurred: \n" + e.getMessage()); //TODO: send to log file
        }
        return entry;
    }
}
