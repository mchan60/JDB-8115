package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.List;

import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class ViewEntryActivity extends AppCompatActivity {
    ImageView image;
    TextView timestamp;
    TextView title;
    TextView description;
    TextView calories;
    TextView happy;

    int entriesIndex;
    String entryTimestamp;

    EntryDB entryDB;
    List<DiaryEntry> entriesList;
    DiaryEntry entry;

    FloatingActionButton backToMainPage;

    FloatingActionButton editDiaryEntry;
    FloatingActionButton deleteDiaryEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        image = (ImageView) (findViewById(R.id.image));
        timestamp = (TextView) (findViewById(R.id.timestamp));
        title = (TextView) (findViewById(R.id.title));
        description = (TextView) (findViewById(R.id.description));
        calories = (TextView) (findViewById(R.id.calories));
        happy = (TextView) (findViewById(R.id.happy));

        backToMainPage = (FloatingActionButton) (findViewById(R.id.backToDiaryButton));

        editDiaryEntry = (FloatingActionButton) (findViewById(R.id.editButton));
        deleteDiaryEntry = (FloatingActionButton) (findViewById(R.id.deleteButton));

        entriesIndex = (int) getIntent().getExtras().get("DIARY_ENTRY");
        entryTimestamp = (String)getIntent().getExtras().get("TIMESTAMP");

        entryDB = new EntryDB(this);
        entriesList = entryDB.getEntries();

        entry = entriesList.get(entriesIndex);

        Uri selectedImage = Uri.parse(entry.getFileUri());
        try {
            image.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        timestamp.setText(entry.getFormattedTime());
        title.setText(entry.getTitle());
        description.setText(entry.getDescription());
        calories.setText(Integer.toString(entry.getCalories())+ " cal.");
        if (entry.getHappy() == 1) {
            happy.setText("Happy!");
        } else {
            happy.setText("Not Happy!");
        }


        backToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBackToMain = new Intent(ViewEntryActivity.this, MainPageActivity.class);
                startActivity(goBackToMain);
            }
        });


        editDiaryEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToEditEntry = new Intent(ViewEntryActivity.this, EditEntryPageActivity.class);
                goToEditEntry.putExtra("DIARY ENTRY", entry);
                goToEditEntry.putExtra("DIARY_ENTRY_ID", entriesIndex);
                startActivity(goToEditEntry);
            }
        });

        deleteDiaryEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBackToMain = new Intent(ViewEntryActivity.this, MainPageActivity.class);
                entryDB.deleteEntry(entry.getTimestamp());
                startActivity(goBackToMain);
            }
        });
    }

}
