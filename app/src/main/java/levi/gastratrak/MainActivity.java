package levi.gastratrak;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<FoodItem> foodsList = new ArrayList<>();
    FoodItemAdapter foodAdapter;
    DatabaseController db = new DatabaseController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        foodAdapter = new FoodItemAdapter(MainActivity.this, R.layout.food_diary_entry, foodsList, db);
        ListView foodDiaryView = findViewById(R.id.foodDiary_list);
        foodDiaryView.setAdapter(foodAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodEmpty();
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            db.addPainRecording(new PainItem(data.getIntArrayExtra("PainArray"), new Time(System.currentTimeMillis())));
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            db.addStoolRecording(new StoolItem(data.getIntArrayExtra("StoolArray"), new Time(System.currentTimeMillis())));
        } else if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            FoodItem item = new FoodItem(data.getStringExtra("foodName"), new Time(data.getLongExtra("foodTime", 0)));
            FoodItem oldItem = new FoodItem(data.getStringExtra("oldFoodName"), new Time(data.getLongExtra("oldFoodTime", 0)));
            db.removeFoodItem(oldItem);
            db.addFoodItem(item);
            foodAdapter.updateFromDatabase();
        } else if (requestCode == 3 && resultCode == RESULT_CANCELED && data != null) {
            FoodItem item = new FoodItem(data.getStringExtra("oldFoodName"), new Time(data.getLongExtra("oldFoodTime", 0)));
            db.removeFoodItem(item);
            foodAdapter.updateFromDatabase();
        }
    }

    public void painScaleOpener(View View) {
        Intent intent = new Intent(this, PainScaleActivity.class);
        startActivityForResult(intent, 1);
    }
    public void graphModeOpener(View View) {
        Intent intent = new Intent(this, GraphingModeActivity.class);
        startActivity(intent);
    }

    public void stoolRecordOpener(View View) {
        Intent intent = new Intent(this, StoolRecordActivity.class);
        startActivityForResult(intent, 2);
    }

    public void editFoodItem(FoodItem item) {
        Intent intent = new Intent(this, FoodDiaryAddEditActivity.class);
        intent.putExtra("foodName", item.getFoodItem());
        intent.putExtra("foodTime", item.getFoodTime().getTime());
        intent.putExtra("isEdit", true);
        startActivityForResult(intent, 3);
    }

    public void addFoodEmpty() {
        Intent intent = new Intent(this, FoodDiaryAddEditActivity.class);
        intent.putExtra("isEdit", false);
        startActivityForResult(intent, 3);
//
//        foodsList.add(new FoodItem("", new Time(System.currentTimeMillis())));
//        this.foodAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_food_diary) {
            // return to the main, not sure if going to implement
            
        } else if (id == R.id.nav_new_pain) {
            painScaleOpener(item.getActionView());
        } else if (id == R.id.nav_stool) {
            stoolRecordOpener(item.getActionView());
        } else if (id == R.id.nav_stats) {
            //TODO make stats
        } else if (id == R.id.nav_graphing) {
            graphModeOpener(item.getActionView());
        } else if (id == R.id.nav_send) {
            //TODO export database
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
