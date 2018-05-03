package com.example.youpiman.mynews.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;


import com.example.youpiman.mynews.Controllers.Activities.NotifActivity;
import com.example.youpiman.mynews.Controllers.Activities.ResultActivity;
import com.example.youpiman.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String MYNEWS_CHANNEL_ID = "com.example.youpiman.mynews";
    private static final String MYNEWS_CHANNEL_NAME = "MYNEWS Channel";

    // For data
    private String query;
    private String from_date;
    private String to_date;
    private String checkboxes;
    private static final String FROM_DATE_VALUE = "20000101";

    public static final String USER_PREF = "USER_PREF";
    public static final String SEARCH_PREF = "SEARCH_PREF";
    public static final String CHECKED_PREF = "CHECKED_PREF";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //Create the channel object with unique ID MYNEWS_CHANNEL_ID
            NotificationChannel channel = new NotificationChannel(MYNEWS_CHANNEL_ID, MYNEWS_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Daily notifications");
            mNotificationManager.createNotificationChannel(channel);
        }

        // Get data from SearchActivity

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, configureIntent(context), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MYNEWS_CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("My News Notification") // title for notification
                .setContentText("It's time for you daily news checking ! ;)")// message for notification
                .setAutoCancel(true); // clear notification after click

        mNotificationManager.notify(NotifActivity.UNIQUE_ID, mBuilder.build());
    }

    // ----------
    // INTENT
    // ----------

    private Intent configureIntent(Context context){
        SharedPreferences preferences = context.getSharedPreferences(USER_PREF, MODE_PRIVATE);

        Intent intent = new Intent(context, ResultActivity.class);

        intent.putExtra(query, preferences.getString(SEARCH_PREF, null));
        intent.putExtra(checkboxes, preferences.getString(CHECKED_PREF, null));
        intent.putExtra(from_date, FROM_DATE_VALUE);
        intent.putExtra(to_date, getCurrentDay());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        return intent;
    }

    // ----------
    // DATA
    // ----------

    // getCurrentDay inside a String
    private String getCurrentDay(){
        Date curDate = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        return format.format(curDate);
    }
}