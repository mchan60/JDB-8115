package edu.awilkins6gatech.happyhealthytummyapp.Controller;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import android.widget.ImageView;

import edu.awilkins6gatech.happyhealthytummyapp.Adapters.ViewPagerAdapter;
import edu.awilkins6gatech.happyhealthytummyapp.Adapters.DailyFragment;

import edu.awilkins6gatech.happyhealthytummyapp.R;

public class MainPageActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 5;

    private Toolbar toolbar;
    private android.support.design.widget.TabLayout tabLayout;
    private android.support.v4.view.ViewPager viewPager;


    Button camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        camera = (Button)findViewById(R.id.camera);

        //setting up
        tabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tabLayout);
        viewPager = (android.support.v4.view.ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adds the tab fragments
        adapter.addFragments(new DailyFragment(), "Daily");
        adapter.addFragments(new DailyFragment(), "Weekly");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);



//        camera.setOnClickListener(new View.OnClickListener() {
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

}


