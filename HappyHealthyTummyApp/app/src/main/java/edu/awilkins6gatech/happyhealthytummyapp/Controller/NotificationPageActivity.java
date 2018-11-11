package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import edu.awilkins6gatech.happyhealthytummyapp.Adapters.CustomListViewAdapter;
import edu.awilkins6gatech.happyhealthytummyapp.Data.NotificationDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.NotificationEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class NotificationPageActivity extends AppCompatActivity {

    Button addNotificationButton;
    ListView listView;

    NotificationDB notificationDB;
    List<NotificationEntry> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addNotificationButton = (Button) findViewById(R.id.addNotificationButton);
        addNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(NotificationPageActivity.this, AddNotificationPageActivity.class);
                startActivity(myIntent);
            }
        });

        notificationDB = new NotificationDB(this);
        notificationList = notificationDB.getEntries();

        listView = (ListView) this.findViewById(R.id.notifications);
        if (notificationList != null) {
            listView.setAdapter(new ArrayAdapter<NotificationEntry> (this,
                    android.R.layout.simple_list_item_1, notificationList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent myIntent = new Intent(NotificationPageActivity.this, AddNotificationPageActivity.class);
                    myIntent.putExtra("NOTIFICATION", notificationList.get(i));
                    startActivity(myIntent);
                }
            });
        }

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.content_notification_page);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());

            }
        });

        //back arrow to main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
