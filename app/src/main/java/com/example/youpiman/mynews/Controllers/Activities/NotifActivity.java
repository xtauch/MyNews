package com.example.youpiman.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.youpiman.mynews.R;
import com.example.youpiman.mynews.Utils.NotificationReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifActivity extends AppCompatActivity {

    @BindView(R.id.activity_notif_toolbar)
    Toolbar mToolbar;

    // Query Search
    @BindView(R.id.form_search_query)
    EditText searchQuery;
    // Dates
    @BindView(R.id.form_search_dates)
    LinearLayout datesLayout;
    // Checkboxes
    @BindView(R.id.form_search_warning_checkboxes)
    TextView mCheckboxesTextview;
    @BindView(R.id.form_search_left_checkBox_arts)
    CheckBox artsCheckBox;
    @BindView(R.id.form_search_left_checkBox_business)
    CheckBox businessCheckBox;
    @BindView(R.id.form_search_left_checkBox_entrepreneurs)
    CheckBox entrepreneursCheckBox;
    @BindView(R.id.form_search_right_checkBox_politics)
    CheckBox politicsCheckBox;
    @BindView(R.id.form_search_right_checkBox_sports)
    CheckBox sportsCheckBox;
    @BindView(R.id.form_search_right_checkBox_travel)
    CheckBox travelCheckBox;
    // Search button
    @BindView(R.id.form_search_button)
    Button searchButton;
    // Notifications switch
    @BindView(R.id.form_search_notif)
    LinearLayout mNotifLayout;
    @BindView(R.id.form_search_switch_notif)
    Switch notifSwitch;

    // For Notification
    public static final int UNIQUE_ID = 100;

    // For Preferences
    private SharedPreferences mPreferences;
    public static final String USER_PREFERENCES = "USER_PREFERENCES";

    // For Data
    public static final String SEARCH_PREF = "SEARCH_PREF";
    public static final String CHECKBOX_PREF = "CHECKBOX_PREF";
    public static final String CHECKED_PREF = "CHECKED_PREF";
    public static final String SWITCH_PREF = "SWITCH_PREF";
    private Set<String> set = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        ButterKnife.bind(this);
        // Set the UI
        mNotifLayout.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.GONE);
        datesLayout.setVisibility(View.GONE);

        // Add back button
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init SharedPreferences
        mPreferences = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);

        // loading last preferences if they exists
        loadingPreferences();

        // Configure the notification switch
        this.configureNotifications();
    }

    // ---------------
    // CONFIGURATION
    // ---------------

    private void configureNotifications(){
        notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (TextUtils.isEmpty(searchQuery.getText())) {
                        searchQuery.setError("This field is required !");
                    }else if(!checkingCheckboxes()){
                        mCheckboxesTextview.setVisibility(View.VISIBLE);
                    } else {
                        savingPreferences();
                        // prepare notification
                        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), UNIQUE_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        // repeat notification every day at 12am
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, 12);
                        calendar.set(Calendar.MINUTE, 30);
                        calendar.set(Calendar.SECOND, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                }
            }
        });
    }

    // ---------------
    // DATA
    // ---------------

    private void savingPreferences(){
        mPreferences.edit().clear().apply();
        mPreferences.edit().putString(SEARCH_PREF, searchQuery.getText().toString()).apply();
        mPreferences.edit().putString(CHECKED_PREF, checkBoxString()).apply();
        mPreferences.edit().putStringSet(CHECKBOX_PREF, set).apply();
        mPreferences.edit().putBoolean(SWITCH_PREF, notifSwitch.isChecked()).apply();
        Log.i("switch is checked: ", ""+notifSwitch.isChecked());
    }

    private void loadingPreferences(){
        // terms of last search
        searchQuery.setText(mPreferences.getString(SEARCH_PREF, null));

        // last checked checkboxes
        set = mPreferences.getStringSet(CHECKBOX_PREF, new HashSet<String>());
        if (set != null && !set.isEmpty()) {
            for (String checkbox : set) {
                switch (checkbox) {
                    case "Arts":
                        artsCheckBox.setChecked(true);
                        break;
                    case "Business":
                        businessCheckBox.setChecked(true);
                        break;
                    case "Entrepreneurs":
                        entrepreneursCheckBox.setChecked(true);
                        break;
                    case "Politics":
                        politicsCheckBox.setChecked(true);
                        break;
                    case "Sports":
                        sportsCheckBox.setChecked(true);
                        break;
                    case "Travel":
                        travelCheckBox.setChecked(true);
                        break;
                    default:
                        Log.e("Checkbox Switch Error: ", checkbox);
                        break;
                }
            }
        }

        // Retrieve last switch position
        Boolean switchBoolean = mPreferences.getBoolean(SWITCH_PREF, false);
        Log.i("switchBoolean", ": "+switchBoolean);
        notifSwitch.setChecked(switchBoolean);
    }

    // Create a piece or URI with checked checkboxes
    @NonNull
    private String checkBoxString(){
        String checkString = null;

        List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(artsCheckBox);
        checkBoxes.add(businessCheckBox);
        checkBoxes.add(entrepreneursCheckBox);
        checkBoxes.add(politicsCheckBox);
        checkBoxes.add(sportsCheckBox);
        checkBoxes.add(travelCheckBox);

        for (CheckBox checkBox: checkBoxes){
            if(checkBox.isChecked() && checkString != null) {
                checkString = checkString+"\" \""+checkBox.getText().toString();
            } else if (checkBox.isChecked() && checkString == null){
                checkString = "news_desk(\""+checkBox.getText().toString();
            }
        }

        return checkString+"\")";
    }

    // Check if one checkbox is checked
    private boolean checkingCheckboxes() {
        Boolean checkCheckboxes = false;

        List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(artsCheckBox);
        checkBoxes.add(businessCheckBox);
        checkBoxes.add(entrepreneursCheckBox);
        checkBoxes.add(politicsCheckBox);
        checkBoxes.add(sportsCheckBox);
        checkBoxes.add(travelCheckBox);

        set = new HashSet<>();

        for (CheckBox checkBox: checkBoxes){
            if (checkBox.isChecked()) {
                checkCheckboxes = true;
                set.add(checkBox.getText().toString());
            }
        }

        return checkCheckboxes;
    }
}