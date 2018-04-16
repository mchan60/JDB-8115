package edu.gatech.healthyhappytummy;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

public class DiaryActivity extends AppCompatActivity {
    private GestureDetectorCompat gestureDetectorCompat;

    private ListView diary_list;
    private LinkedList<FoodEntry> entry_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Diary");

        diary_list = (ListView) findViewById(R.id.diary_entries);
        Intent in = getIntent();
        entry_list = new LinkedList<>();
        entry_list.add((FoodEntry)in.getSerializableExtra("entry"));
        ListAdapter array_adapter = new ArrayAdapter<>(this, R.layout.activity_diary_listview, R.id.textView, entry_list);
        diary_list.setAdapter(array_adapter);
        diary_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(DiaryActivity.this, ViewEntryActivity.class);
                myIntent.putExtra("DIARY_ENTRY", entry_list.get(position));
                startActivity(myIntent);
            }
        });

        gestureDetectorCompat = new GestureDetectorCompat(this, new My2ndGestureListener());


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class My2ndGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe right' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if(event2.getX() > event1.getX()){
                Toast.makeText(getBaseContext(),
                        "Swipe right - finish()",
                        Toast.LENGTH_SHORT).show();

                finish();
            }

            return true;
        }
    }

}


