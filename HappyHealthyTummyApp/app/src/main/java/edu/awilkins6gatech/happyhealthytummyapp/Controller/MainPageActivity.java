package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class MainPageActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 5;
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;
    List<DiaryEntry> diaryEntries;
    List<File> diaryJsonFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);

        DiaryEntry dummyEntryForData = new DiaryEntry();
        diaryJsonFiles = dummyEntryForData.getDiaryEntries();
        ObjectMapper mapper = new ObjectMapper();
        if (diaryJsonFiles != null) {
            for (File file : diaryJsonFiles) {
                try {
                    diaryEntries.add(mapper.readValue(file, DiaryEntry.class));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("file not found to be added to entries list");
                }
            }

            Uri selectedImage2 = diaryEntries.get(diaryEntries.size() - 1).getFileUri();
            Uri selectedImage3 = diaryEntries.get(diaryEntries.size() - 2).getFileUri();

            try {
                imageView2.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage2)));
                System.out.println("image 2 found");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("image 2 not found");
            }

            try {
                imageView3.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage3)));
                System.out.println("image 3 found");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("image 3 not found");
            }
        } else {
            System.out.println("directory returned null on main");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent goToLandingPage = new Intent(MainPageActivity.this, LandingActivity.class);
                startActivity(goToLandingPage);
            }
        });
        Bundle retrievalData = getIntent().getExtras();
        if (retrievalData != null) {
            String uriAsAString = (String) retrievalData.get("File Uri");
            Uri selectedImage = Uri.parse(uriAsAString);
            try {
                imageView.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage)));
                System.out.println("file was found");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("file wasn't found from main page");
            }
        } else {
            System.out.println("intent's extras are null for some reason");
        }
    }
}


