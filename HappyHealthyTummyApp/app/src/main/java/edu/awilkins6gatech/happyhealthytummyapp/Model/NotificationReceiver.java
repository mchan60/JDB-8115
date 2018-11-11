package edu.awilkins6gatech.happyhealthytummyapp.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Matt on 11/11/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, NotificationIntentService.class);
        context.startActivity(myIntent);
    }
}
