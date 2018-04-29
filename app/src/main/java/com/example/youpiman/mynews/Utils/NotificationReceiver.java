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

    private static final String MYNEWS_CHANNEL_ID = "com.zafindratafa.terence.mynews";
    private static final String MYNEWS_CHANNEL_NAME = "MYNEWS Channel";

    // For data
    private String QUERY;
    private String FROM_DATE;
    private String TO_DATE;
    private String CHECKBOXES;
    private static final String FROM_DATE_VALUE = "20000101";

    // For Preferences
    private SharedPreferences mPreferences;
    public static final String USER_PREFERENCES = "USER_PREFERENCES";
    public static final String SEARCH_PREF = "SEARCH_PREF";
    public static final String CHECKED_PREFERENCES = "CHECKED_PREF";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(MYNEWS_CHANNEL_ID,
                    MYNEWS_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
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
        mPreferences = context.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);

        Intent intent = new Intent(context, ResultActivity.class);

        intent.putExtra(QUERY, mPreferences.getString(SEARCH_PREF, null));
        intent.putExtra(CHECKBOXES, mPreferences.getString(CHECKED_PREFERENCES, null));
        intent.putExtra(FROM_DATE, FROM_DATE_VALUE);
        intent.putExtra(TO_DATE, getCurrentDay());
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
        String dateToStr = format.format(curDate);

        return dateToStr;
    }
}