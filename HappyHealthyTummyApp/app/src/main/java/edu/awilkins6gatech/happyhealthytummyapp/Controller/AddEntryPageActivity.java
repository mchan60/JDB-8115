package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class AddEntryPageActivity extends AppCompatActivity {

    Button postEntryButton;
    ImageView foodImage;
    Uri selectedImage;
    Bitmap bitmap;

    EditText title;
    EditText description;
    EditText calories;
    Switch happy;

    ArrayList<DiaryEntry> diaryEntries;

    EntryDB entriesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postEntryButton = (Button)(findViewById(R.id.postEntryButton));
        foodImage = (ImageView)(findViewById(R.id.foodImage));

        title = (EditText)(findViewById(R.id.title));
        description = (EditText)(findViewById(R.id.description));
        calories = (EditText)(findViewById(R.id.calories));
        happy = (Switch)(findViewById(R.id.switch1));

        diaryEntries = new ArrayList<DiaryEntry>();
        entriesDB = new EntryDB(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        selectedImage = Uri.parse( (String) getIntent().getExtras().get("File Uri"));
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("bitmap not made");
        }
        try {
            foodImage.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage)));
            System.out.println("bitmap printed at addentry");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("couldn't print bitmap at addentry");
        }

        postEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiaryEntry();
                Intent goToMainPage = new Intent(AddEntryPageActivity.this, MainPageActivity.class);
                goToMainPage.putExtra("File Uri", (String) getIntent().getExtras().get("File Uri"));
                startActivity(goToMainPage);
            }
        });

    }

    String newTitle, newDescription;
    int newCalories, newHappy;
    private void addDiaryEntry() {
        newTitle = title.getText().toString();
        newDescription = description.getText().toString();
        newCalories = Integer.parseInt(calories.getText().toString());

        if (happy.isChecked()) {
            newHappy = 1;
        } else {
            newHappy = 0;
        }
        DiaryEntry newDiaryEntry = new DiaryEntry((String) getIntent().getExtras().get("File Uri"),newCalories,
                (String) getIntent().getExtras().get("Time Stamp"), newTitle, newDescription, newHappy);
        diaryEntries.add(newDiaryEntry);
        entriesDB.addEntry(newDiaryEntry);
    }

}
