package com.example.youpiman.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.youpiman.mynews.Adapters.PageAdapter;
import com.example.youpiman.mynews.Controllers.Fragments.BusinessFragment;
import com.example.youpiman.mynews.Controllers.Fragments.MostPopularFragment;
import com.example.youpiman.mynews.Controllers.Fragments.TopStoriesFragment;
import com.example.youpiman.mynews.R;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @BindView(R.id.activity_main_viewpager) ViewPager pager;

    //FOR DATA
    // 2 - Identify each fragment with a number
    private static final int FRAGMENT_TOPSTORIES = 0;
    private static final int FRAGMENT_MOSTPOPULAR = 1;
    private static final int FRAGMENT_BUSINESS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Configuring Toolbar
        this.configureToolbar();

        // Configure ViewPager and Tabs
        this.configureViewPagerAndTabs();

        // Configure DrawerLayout
        this.configureDrawerLayout();

        // Configure NavigationView
        this.configureNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_activity_main_search :
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.menu_activity_main_settings :
                startActivity(new Intent(this, NotifActivity.class));
                break;
            default:
               break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_topstories :
                pager.setCurrentItem(FRAGMENT_TOPSTORIES);
                break;
            case R.id.activity_main_drawer_mostpopular:
                pager.setCurrentItem(FRAGMENT_MOSTPOPULAR);
                break;
            case R.id.activity_main_drawer_business:
                pager.setCurrentItem(FRAGMENT_BUSINESS);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    // ---------------------
    // CONFIG
    // ---------------------

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        this.toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    private void configureViewPagerAndTabs(){

        //Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), this));
        //  Get TabLayout from layout
        TabLayout tabs= findViewById(R.id.activity_main_tabs);
        //  Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
