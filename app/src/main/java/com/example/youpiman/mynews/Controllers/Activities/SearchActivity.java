package com.example.youpiman.mynews.Controllers.Activities;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.youpiman.mynews.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.activity_search_toolbar)
    Toolbar mToolbar;

    // Query Search
    @BindView(R.id.form_search_query)
    EditText searchQuery;
    // Period of time
    @BindView(R.id.form_search_fromdate)
    EditText searchFromDate;
    @BindView(R.id.form_search_todate)
    EditText searchToDate;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        // Add back button
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configure the two DatePickerDialogs
        this.configureEditText(searchFromDate);
        this.configureEditText(searchToDate);

        // Configure the launch button
        launchResearch();
    }

    // ---------------
    // ACTION
    // ---------------

    private void launchResearch(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(searchQuery.getText())) {
                    searchQuery.setError("This field is required !");
                } else if (checkingCheckboxes() == false){
                    mCheckboxesTextview.setVisibility(View.VISIBLE);
                } else {
                    Intent intent = new Intent(SearchActivity.this, ResultActivity.class);

                    // get the query term(s)
                    intent.putExtra("QUERY", searchQuery.getText().toString());

                    // get the begin date
                    String fromDate = searchFromDate.getText().toString();
                    Log.e("fromDate", searchFromDate.getText().toString());
                    if (fromDate.length() <= 1) {
                        fromDate = "20000101";
                    } else {
                        fromDate = fromDate.replace(" / ", "");
                    }
                    intent.putExtra("FROM_DATE", fromDate);

                    // get the end date
                    String toDate = searchToDate.getText().toString();
                    Log.e("toDate", searchToDate.getText().toString());
                    if (toDate.length() <= 1){
                        toDate = getCurrentDay();
                    } else {
                        toDate = toDate.replace(" / ", "");
                    }
                    intent.putExtra("TO_DATE", toDate);

                    // get checkboxes
                    intent.putExtra("CHECKBOXES", checkBoxString());
                    startActivity(intent);
                }
            }
        });
    }

    // ---------------
    // CONFIGURATION
    // ---------------

    private void configureEditText(final EditText editText){
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog picker;

                picker = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sMonth;
                        if (month < 10) {
                            sMonth = "0" + (month + 1);
                        } else {
                            sMonth = Integer.toString(month + 1);
                        }
                        String sDayOfMonth;
                        if (dayOfMonth < 10) {
                            sDayOfMonth = "0"+(dayOfMonth);
                        } else {
                            sDayOfMonth = Integer.toString(dayOfMonth);
                        }
                        editText.setText(year+" / "+sMonth+" / "+sDayOfMonth);
                    }
                }, year, month, day);
                picker.show();
            }
        });
    }

    // -------------
    // UTILS
    // -------------

    // getCurrentDay into a String
    private String getCurrentDay(){
        Date curDate = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateToStr = format.format(curDate);

        return dateToStr;
    }


    // Create a piece or URI with checked checkboxes
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

        for (CheckBox checkBox: checkBoxes){
            if (checkBox.isChecked()) checkCheckboxes = true;
        }

        return checkCheckboxes;
    }
}