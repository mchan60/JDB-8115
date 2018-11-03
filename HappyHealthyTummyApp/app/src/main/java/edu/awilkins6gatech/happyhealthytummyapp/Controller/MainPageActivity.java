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



import edu.awilkins6gatech.happyhealthytummyapp.Data.EntryDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;
import edu.awilkins6gatech.happyhealthytummyapp.adapters.CustomListViewAdapter;

public class MainPageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int RESULT_LOAD_IMAGE = 5;
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;
    List<DiaryEntry> diaryEntries;
    List<File> diaryJsonFiles;

    EntryDB entryDB;


    SparseArray<DiaryEntry> entriesMap;

    public static final Integer[] images = { R.id.imageView,
            R.id.imageView2, R.id.imageView3};

    ListView listView;
    List<DiaryEntry> entriesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);

        entryDB = new EntryDB(this);
        entriesList = entryDB.getEntries();


        listView = findViewById(R.id.list);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.list_item, entriesList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);



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


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + entriesList.get(position),
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


