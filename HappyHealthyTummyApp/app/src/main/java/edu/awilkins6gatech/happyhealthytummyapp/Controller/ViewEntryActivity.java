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

    EntryDB entryDB;
    List<DiaryEntry> entriesList;

    Button backToMainPage;

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

        backToMainPage = (Button) (findViewById(R.id.backToDiaryButton));

        entriesIndex = (int) getIntent().getExtras().get("DIARY_ENTRY");

        entryDB = new EntryDB(this);
        entriesList = entryDB.getEntries();

        Uri selectedImage = Uri.parse(entriesList.get(entriesIndex).getFileUri());
        try {
            image.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        timestamp.setText(entriesList.get(entriesIndex).getTimestamp());
        title.setText(entriesList.get(entriesIndex).getTitle());
        description.setText(entriesList.get(entriesIndex).getDescription());
        calories.setText(Integer.toString(entriesList.get(entriesIndex).getCalories()));
        if (entriesList.get(entriesIndex).getHappy() == 1) {
            happy.setText("Happy!");
        } else {
            happy.setText("Not Happy!");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        backToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBackToMain = new Intent(ViewEntryActivity.this, MainPageActivity.class);
                startActivity(goBackToMain);
            }
        });
    }

}
