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
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import edu.awilkins6gatech.happyhealthytummyapp.Adapters.CustomListViewAdapter;
import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class MainPageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int RESULT_LOAD_IMAGE = 5;
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;
    List<DiaryEntry> diaryEntries;
    List<File> diaryJsonFiles;

    EntryDB entryDB;
    List<DiaryEntry> entriesList;
    ListView listView;

//    SparseArray<DiaryEntry> entriesMap;

    Button camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        camera = (Button)findViewById(R.id.camera);
        entryDB = new EntryDB(this);
        entriesList = entryDB.getEntries();

        listView = (ListView) findViewById(R.id.diaryEntries);
        if (listView == null) System.out.println("list view is null in main FIX IT !!!!!!!");


        //Adapter to populate the page with image list view
        if (entriesList != null) {
            CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                    R.id.diaryEntries, entriesList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent goToViewEntryPage = new Intent(MainPageActivity.this, ViewEntryActivity.class);
                    goToViewEntryPage.putExtra("DIARY_ENTRY", ((int) l));
                    goToViewEntryPage.putExtra("TIMESTAMP", entriesList.get((int) l).getTimestamp());
                    startActivity(goToViewEntryPage);
                }

            }
        );

        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLandingPage = new Intent(MainPageActivity.this, LandingActivity.class);
                startActivity(goToLandingPage);
            }
        });




        //from before
//        entriesMap = new SparseArray<DiaryEntry>();
//        for (int i = 0; i < entriesList.size() - 1; i++) {
//            entriesMap.append(i, entriesList.get(i));
//        }
//
//        if (entriesList.size() > 0) {
//            Uri selectedImage2 = Uri.parse(entriesList.get(entriesList.size() - 1).getFileUri());
//            try {
//                imageView2.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage2)));
//                System.out.println("image 2 found");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                System.out.println("image 2 not found");
//            }
//        }
//        //TODO: view entry page currently hardcoded on image 2 to go to size -1, map is made to try and get key and index without hardcoding
//        imageView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent goToViewEntryPage = new Intent(MainPageActivity.this, ViewEntryActivity.class);
//                goToViewEntryPage.putExtra("DIARY_ENTRY", entriesList.size() - 1);
//                goToViewEntryPage.putExtra("TIMESTAMP", entriesList.get(entriesList.size() - 1).getTimestamp());
//                startActivity(goToViewEntryPage);
//            }
//        });
//
//        if (entriesList.size() > 1) {
//            Uri selectedImage3 = Uri.parse(entriesList.get(entriesList.size() - 2).getFileUri());
//
//            try {
//                imageView3.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage3)));
//                System.out.println("image 3 found");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                System.out.println("image 3 not found");
//            }
//            //TODO:currently hardcoded to entries size - 2
//            imageView3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent goToViewEntryPage = new Intent(MainPageActivity.this, ViewEntryActivity.class);
//                    goToViewEntryPage.putExtra("DIARY_ENTRY", entriesList.size() - 2);
//                    goToViewEntryPage.putExtra("TIMESTAMP", entriesList.get(entriesList.size() - 2).getTimestamp());
//                    startActivity(goToViewEntryPage);
//                }
//            });
//        }
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                Intent goToLandingPage = new Intent(MainPageActivity.this, LandingActivity.class);
//                startActivity(goToLandingPage);
//            }
//        });
//        Bundle retrievalData = getIntent().getExtras();
//        if (retrievalData != null) {
//            String uriAsAString = (String) retrievalData.get("File Uri");
//            Uri selectedImage = Uri.parse(uriAsAString);
//            try {
//                imageView.setImageBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage)));
//                System.out.println("file was found");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                System.out.println("file wasn't found from main page");
//            }
//        } else {
//            System.out.println("intent's extras are null for some reason");
//        }

//        // Create a dummy list of 20 images
//        ArrayList<String> entryList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            entryList.add("");
//        }
//
//        ArrayAdapter<String> entryAdapter = new EntriesAdapter<String>(this,
//                R.id.entry_list_template, entryList);
//
//        ListView listView = (ListView) this.findViewById(R.id.entries_list);
//        listView.setAdapter(entryAdapter);


        



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + diaryEntries.get(position),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public static <T, E> T getKeyFromValue(E value, Map<T, E> map) {
        for (Map.Entry<T, E> entry: map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}


