package levi.gastratrak;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.io.IOException;


public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        FoodDiaryFragment.OnFragmentInteractionListener,
        GraphFragment.OnFragmentInteractionListener,
        StatsFragment.OnFragmentInteractionListener{
    private java.lang.Class currentFragmentClass = FoodDiaryFragment.class;
    private Fragment currentFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass;
            fragmentClass = FoodDiaryFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean change = false;
        if (id == R.id.nav_food_diary) {
            change = currentFragmentClass != FoodDiaryFragment.class;
            currentFragmentClass = FoodDiaryFragment.class;
        } else if (id == R.id.nav_graphing) {
            change = currentFragmentClass != GraphFragment.class;
            currentFragmentClass = GraphFragment.class;
        } else if (id == R.id.nav_send) {
            //TODO export database
        } else if (id == R.id.nav_stats) {
            change = currentFragmentClass != StatsFragment.class;
            currentFragmentClass = StatsFragment.class;
        }
        if((id == R.id.nav_graphing || id == R.id.nav_food_diary || id == R.id.nav_stats) && change) {
            try {
                currentFragment = (Fragment) currentFragmentClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, currentFragment).commit();
        }
        if (id == R.id.nav_new_pain) {
            painScaleOpener();
        } else if (id == R.id.nav_stool) {
            stoolRecordOpener();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private void painScaleOpener() {
        Intent intent = new Intent(this, PainScaleActivity.class);
        startActivity(intent);
    }

    private void stoolRecordOpener() {
        Intent intent = new Intent(this, StoolRecordActivity.class);
        startActivity(intent);
    }
}