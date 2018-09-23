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
import android.widget.ImageView;

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

        diaryEntries = new ArrayList<DiaryEntry>();
        entriesDB = new EntryDB(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                DiaryEntry newDiaryEntry = new DiaryEntry((String) getIntent().getExtras().get("File Uri"),0,
                        (String) getIntent().getExtras().get("Time Stamp"), "title", "description", 0);
                diaryEntries.add(newDiaryEntry);
                entriesDB.addEntry(newDiaryEntry);
                newDiaryEntry.createEntry(newDiaryEntry);
                Intent goToMainPage = new Intent(AddEntryPageActivity.this, MainPageActivity.class);
                goToMainPage.putExtra("File Uri", (String) getIntent().getExtras().get("File Uri"));
                startActivity(goToMainPage);
            }
        });

    }

}
