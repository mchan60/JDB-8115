package edu.awilkins6gatech.happyhealthytummyapp.Controller;
import android.content.Intent;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;

import edu.awilkins6gatech.happyhealthytummyapp.Adapters.ViewPagerAdapter;
import edu.awilkins6gatech.happyhealthytummyapp.Adapters.DailyFragment;

import edu.awilkins6gatech.happyhealthytummyapp.Adapters.WeeklyFragment;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class MainPageActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 5;

    private Toolbar toolbar;
    private android.support.design.widget.TabLayout tabLayout;
    private android.support.v4.view.ViewPager viewPager;


    private DrawerLayout mDrawerLayout;



    private FloatingActionButton camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        camera = (FloatingActionButton)findViewById(R.id.addEntryButtonHome);

        //setting up
        tabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tabLayout);
        viewPager = (android.support.v4.view.ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adds the tab fragments
        adapter.addFragments(new DailyFragment(), "Daily");
        adapter.addFragments(new WeeklyFragment(), "Weekly");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);



//        camera.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent goToLandingPage = new Intent(MainPageActivity.this, LandingActivity.class);
//                startActivity(goToLandingPage);
//            }
//        });


    }

    public void refreshNow (){
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
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

}


