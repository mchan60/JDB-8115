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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private DrawerLayout mDrawerLayout;

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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + diaryEntries.get(position),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    //menu functionality/////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.notifications:
                Intent gotToNotifications = new Intent(MainPageActivity.this, NotificationPageActivity.class);
                startActivity(gotToNotifications);
                return true;
            case R.id.recipeSearch:
                Intent gotToRecipeSearch = new Intent(MainPageActivity.this, RecipeSearchActivity.class);
                startActivity(gotToRecipeSearch);
                return true;
            case R.id.dietSearch:
                Intent gotToDietSearch = new Intent(MainPageActivity.this, DietSearchActivity.class);
                startActivity(gotToDietSearch);
                return true;
            case R.id.feedback:
                Intent gotToFeedback = new Intent(MainPageActivity.this, FeedbackActivity.class);
                startActivity(gotToFeedback);
                return true;
            case R.id.monthly:
                Intent goToMonthly = new Intent(MainPageActivity.this, CalendarActivity.class);
                startActivity(goToMonthly);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //////////////////////////////////////////////////

    public static <T, E> T getKeyFromValue(E value, Map<T, E> map) {
        for (Map.Entry<T, E> entry: map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}


