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

public class EditEntryPageActivity extends AppCompatActivity {

    FloatingActionButton doneEntryButton;
    ImageView foodImage;
    Uri selectedImage;
    Bitmap bitmap;

    EditText title;
    EditText description;
    EditText calories;
    Switch happy;

    ArrayList<DiaryEntry> diaryEntries;

    EntryDB entriesDB;
    DiaryEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        doneEntryButton = (FloatingActionButton)(findViewById(R.id.doneEntryButton));
        foodImage = (ImageView)(findViewById(R.id.foodImage));

        title = (EditText)(findViewById(R.id.title));
        description = (EditText)(findViewById(R.id.description));
        calories = (EditText)(findViewById(R.id.calories));
        happy = (Switch)(findViewById(R.id.switch1));

        diaryEntries = new ArrayList<DiaryEntry>();
        entriesDB = new EntryDB(this);
        entry = (DiaryEntry) getIntent().getExtras().get("DIARY ENTRY");

        title.setText(entry.getTitle());
        description.setText(entry.getDescription());
        calories.setText(Integer.toString(entry.getCalories()));
        happy.setChecked(entry.getHappy() == 1);

        selectedImage = Uri.parse(entry.getFileUri());
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

        doneEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDiaryEntry();
                Intent goToMainPage = new Intent(EditEntryPageActivity.this, ViewEntryActivity.class);
                goToMainPage.putExtra("File Uri", (String) getIntent().getExtras().get("File Uri"));
                goToMainPage.putExtra("DIARY_ENTRY", (int) getIntent().getExtras().get("DIARY_ENTRY_ID"));
                goToMainPage.putExtra("TIMESTAMP",(String)getIntent().getExtras().get("TIMESTAMP"));
                startActivity(goToMainPage);
            }
        });

    }

    String newTitle, newDescription;
    int newCalories, newHappy;
    private void editDiaryEntry() {
        newTitle = title.getText().toString();
        newDescription = description.getText().toString();
        newCalories = Integer.parseInt(calories.getText().toString());

        if (happy.isChecked()) {
            newHappy = 1;
        } else {
            newHappy = 0;
        }

            DiaryEntry updatedDiaryEntry = entry;
            entry.setTitle(newTitle);
            entry.setDescription(newDescription);
            entry.setCalories(newCalories);
            entry.setHappy(newHappy);
            diaryEntries.add(updatedDiaryEntry);
            entriesDB.editEntry(entry);
    }

}
