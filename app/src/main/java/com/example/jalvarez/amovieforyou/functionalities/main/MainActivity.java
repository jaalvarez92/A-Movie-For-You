package com.example.jalvarez.amovieforyou.functionalities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jalvarez.amovieforyou.R;
import com.example.jalvarez.amovieforyou.functionalities.main.latestrecommendations.LatestRecommendationsFragment;
import com.example.jalvarez.amovieforyou.functionalities.main.latestrecommendations.LatestRecommendationsPresenter;
import com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas.NowOnCinemasFragment;
import com.example.jalvarez.amovieforyou.functionalities.main.nowoncinemas.NowOnCinemasPresenter;
import com.example.jalvarez.amovieforyou.util.ActivityUtils;

/**
 * Created by jalvarez on 1/13/17.
 * This is a file created for the project A-Movie-For-You
 *
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */


public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Setup the navigation drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        // Set the fragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = LatestRecommendationsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        if (fragment instanceof LatestRecommendationsFragment) {
            new LatestRecommendationsPresenter((LatestRecommendationsFragment) fragment);
        }
        else if (fragment instanceof NowOnCinemasFragment) {
            new NowOnCinemasPresenter((NowOnCinemasFragment) fragment);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_latests:
                                // Set the fragment
                                LatestRecommendationsFragment latestRecommendationsFragment = LatestRecommendationsFragment.newInstance();
                                ActivityUtils.replaceFragmentInActivity(
                                        getSupportFragmentManager(), latestRecommendationsFragment, R.id.contentFrame);
                                new LatestRecommendationsPresenter(latestRecommendationsFragment);
                                break;
                            case R.id.nav_now_on_cinemas:
                                NowOnCinemasFragment nowOnCinemasFragment = NowOnCinemasFragment.newInstance();
                                ActivityUtils.replaceFragmentInActivity(
                                        getSupportFragmentManager(), nowOnCinemasFragment, R.id.contentFrame);

                                new NowOnCinemasPresenter(nowOnCinemasFragment);
                                break;

                        }

                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
