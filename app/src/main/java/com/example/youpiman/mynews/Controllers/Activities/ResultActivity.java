package com.example.youpiman.mynews.Controllers.Activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.youpiman.mynews.Controllers.Fragments.ResultFragment;
import com.example.youpiman.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    // For design
    @BindView(R.id.activity_result_toolbar)
    Toolbar mToolbar;

    // FOR DATA
    private String query;
    private String from_date;
    private String to_date;
    private String checkboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        this.configureToolbar();

        // Get data from SearchActivity
        query = getIntent().getStringExtra("QUERY");
        from_date = getIntent().getStringExtra("FROM_DATE");
        to_date = getIntent().getStringExtra("TO_DATE");
        checkboxes = getIntent().getStringExtra("CHECKBOXES");

        sendData();
    }

    // --------------
    // CONFIGURATION
    // --------------

    private void configureToolbar(){
        // Add back button
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sendData() {
        // Pack data in a bundle
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        bundle.putString("from_date", from_date);
        bundle.putString("to_date", to_date);
        bundle.putString("checkboxes", checkboxes);

        // Pass over the bundle to our fragment
        Fragment resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);

        // Then show our fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, resultFragment).commit();
    }
}