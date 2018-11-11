package edu.awilkins6gatech.happyhealthytummyapp.Model;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;

import edu.awilkins6gatech.happyhealthytummyapp.Controller.NotificationPageActivity;
import edu.awilkins6gatech.happyhealthytummyapp.R;


/**
 * Created by Matt on 11/11/2018.
 */

public class NotificationIntentService extends IntentService{
    private static final int NOTIFICATION_ID = 3;

    public NotificationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Eat");
        builder.setContentText("It is time to eat");
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent notifyIntent = new Intent(this, NotificationPageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
