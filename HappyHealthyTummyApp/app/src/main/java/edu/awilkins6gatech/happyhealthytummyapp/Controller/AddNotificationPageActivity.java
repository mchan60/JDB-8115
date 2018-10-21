package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import edu.awilkins6gatech.happyhealthytummyapp.R;

public class AddNotificationPageActivity extends AppCompatActivity {


    TimePicker timePicker;
    Button addNotificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        addNotificationButton = (Button) findViewById(R.id.addNotificationButton);
        addNotificationButton.setOnClickListener();

        timePicker = (TimePicker) findViewById(R.id.timePicker);
    }

}
