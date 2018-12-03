package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class FeedbackActivity extends AppCompatActivity {

    TextView averageCalories;
    TextView happyPercentage;
    TextView firstEntry;
    TextView lastEntry;
    EntryDB entryDB;
    List<DiaryEntry> diaryEntryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        entryDB = new EntryDB(this);
        diaryEntryList = entryDB.getEntries();

        averageCalories = findViewById(R.id.AverageCalories);
        happyPercentage = findViewById(R.id.HappyPercentage);
        firstEntry = findViewById(R.id.FirstEntry);
        lastEntry = findViewById(R.id.LastEntry);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        firstEntry.setText("First Entry: " + diaryEntryList.get(0).getTitle());
        lastEntry.setText("Last Entry: " + diaryEntryList.get(diaryEntryList.size() - 1).getTitle());
        happyPercentage.setText("Percentage Happy: " + getHappyPercentage());
        averageCalories.setText("Average Calories: " + getCalorieAverage());



        //back arrow to main page
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public float getHappyPercentage() {
        float percentage = 0;
        float happiness = 0;
        float total = diaryEntryList.size();
        for (int i = 0; i < diaryEntryList.size(); i++) {
            if (diaryEntryList.get(i).getHappy() == 1) {
                happiness++;
            }
        }
        percentage = happiness/total;
        return percentage;
    }

    public float getCalorieAverage() {
        float avg = 0;
        float total = diaryEntryList.size();
        float amount = 0;
        for (int i = 0; i < diaryEntryList.size(); i++) {
            amount += diaryEntryList.get(i).getCalories();
        }
        avg = amount/total;
        return avg;
    }

}
