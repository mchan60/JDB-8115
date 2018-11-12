package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import java.util.Calendar;

import edu.awilkins6gatech.happyhealthytummyapp.Data.NotificationDB;
import edu.awilkins6gatech.happyhealthytummyapp.Model.NotificationEntry;
import edu.awilkins6gatech.happyhealthytummyapp.Model.NotificationReceiver;
import edu.awilkins6gatech.happyhealthytummyapp.R;

public class AddNotificationPageActivity extends AppCompatActivity {

    TimePicker timePicker;
    Button saveNotificationButton;
    Button deleteNotificationButton;

    RadioGroup mealGroup;

    NotificationDB notificationDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notificationDB = new NotificationDB(this);

        saveNotificationButton = (Button) findViewById(R.id.saveNotificationButton);
        Bundle extras = getIntent().getExtras();

        saveNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getExtras() == null) {
                    addNotification();
                } else {
                    editNotification(((NotificationEntry) getIntent().getExtras().get("NOTIFICATION")).getId());
                }
                Intent goToNotificationPage = new Intent(AddNotificationPageActivity.this, NotificationPageActivity.class);
                startActivity(goToNotificationPage);
            }
        });

        deleteNotificationButton = (Button) findViewById(R.id.deleteNotificationButton);
        deleteNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AddNotificationPageActivity.this, NotificationPageActivity.class);
                notificationDB.deleteEntry(((NotificationEntry) getIntent().getExtras().get("NOTIFICATION")).getId());
                startActivity(myIntent);
            }
        });

        mealGroup = (RadioGroup) findViewById(R.id.mealGroup);
        mealGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                saveNotificationButton.setClickable(true);
            }
        });

        timePicker = (TimePicker) findViewById(R.id.timePicker);

        if (extras == null) {
            saveNotificationButton.setClickable(false);
        } else {
            NotificationEntry entry = (NotificationEntry) extras.get("NOTIFICATION");
            timePicker.setCurrentHour(Integer.parseInt(entry.getTime().split(":")[0]));
            timePicker.setCurrentMinute(Integer.parseInt(entry.getTime().split(":")[1]));
            switch (entry.getMeal()) {
                case "Breakfast":
                    mealGroup.check(R.id.breakfastButton);
                    break;
                case "Lunch":
                    mealGroup.check(R.id.lunchButton);
                    break;
                case "Dinner":
                    mealGroup.check(R.id.dinnerButton);
                    break;
                case "Snack":
                    mealGroup.check(R.id.snackButton);
                    break;
                default:
                    break;
            }
            deleteNotificationButton.setVisibility(View.VISIBLE);
            deleteNotificationButton.setClickable(true);
        }
    }

    private void editNotification(int id) {
        NotificationEntry notification = new NotificationEntry();
        notification.setMeal(((RadioButton) findViewById(mealGroup.getCheckedRadioButtonId())).getText().toString());
        notification.setTime(String.format("%02d:%02d", timePicker.getCurrentHour() ,timePicker.getCurrentMinute()));
        notificationDB.editEntry(notification, id);

        Intent notifyIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 999, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

        Calendar calendar = Calendar.getInstance();

        Calendar notificationCalendar = Calendar.getInstance();
        notificationCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        notificationCalendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        notificationCalendar.set(Calendar.SECOND, 0);

        if (notificationCalendar.before(calendar)) {
            notificationCalendar.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  notificationCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void addNotification() {
        NotificationEntry notification = new NotificationEntry();
        notification.setMeal(((RadioButton) findViewById(mealGroup.getCheckedRadioButtonId())).getText().toString());
        notification.setTime(String.format("%02d:%02d", timePicker.getCurrentHour() ,timePicker.getCurrentMinute()));
        notificationDB.addEntry(notification);

        Intent notifyIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 999, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        Calendar notificationCalendar = Calendar.getInstance();
        notificationCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        notificationCalendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        notificationCalendar.set(Calendar.SECOND, 0);

        if (notificationCalendar.before(calendar)) {
            notificationCalendar.add(Calendar.DATE, 1);
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP,  notificationCalendar.getTimeInMillis(), pendingIntent);
    }

}
