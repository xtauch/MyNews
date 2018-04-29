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
    private String QUERY;
    private String FROM_DATE;
    private String TO_DATE;
    private String CHECKBOXES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        this.configureToolbar();

        // Get data from SearchActivity
        QUERY = getIntent().getStringExtra("QUERY");
        FROM_DATE = getIntent().getStringExtra("FROM_DATE");
        TO_DATE = getIntent().getStringExtra("TO_DATE");
        CHECKBOXES = getIntent().getStringExtra("CHECKBOXES");

        sendData();
    }

    // --------------
    // CONFIGURATION
    // --------------

    private void configureToolbar(){
        // Add back button
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void sendData() {
        // Pack data in a bundle
        Bundle bundle = new Bundle();
        bundle.putString("query", QUERY);
        bundle.putString("from_date", FROM_DATE);
        bundle.putString("to_date", TO_DATE);
        bundle.putString("checkboxes", CHECKBOXES);

        // Pass over the bundle to our fragment
        Fragment resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);

        // Then show our fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, resultFragment).commit();
    }
}