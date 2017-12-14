package levi.gastratrak;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final DatabaseController db = new DatabaseController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        GraphView graph = findViewById(R.id.graph);
        ArrayList<PainItem> painArray = new ArrayList<>();
        painArray.addAll(db.getAllPainItems());
        DataPoint[] totalPainValues = new DataPoint[painArray.size()];
        DataPoint[] otherPainValues = new DataPoint[painArray.size()];
        DataPoint[] upperPainValues = new DataPoint[painArray.size()];
        DataPoint[] lowerPainValues = new DataPoint[painArray.size()];
        for (int i = 0; i < painArray.size(); i++) {
            totalPainValues[i] = new DataPoint(i+1, painArray.get(i).getPainLevel()[0]);
            otherPainValues[i] = new DataPoint(i+1, painArray.get(i).getPainLevel()[1]);
            upperPainValues[i] = new DataPoint(i+1, painArray.get(i).getPainLevel()[2]);
            lowerPainValues[i] = new DataPoint(i+1, painArray.get(i).getPainLevel()[3]);
        }
        BarGraphSeries<DataPoint> painTotal = new BarGraphSeries<>(totalPainValues);
        BarGraphSeries<DataPoint> painOther = new BarGraphSeries<>(otherPainValues);
        BarGraphSeries<DataPoint> painUpper = new BarGraphSeries<>(upperPainValues);
        BarGraphSeries<DataPoint> painLower = new BarGraphSeries<>(lowerPainValues);
        Paint totalColor = new Paint();
        Paint otherColor = new Paint();
        Paint upperColor = new Paint();
        Paint lowerColor = new Paint();
        totalColor.setColor(Color.BLACK);
        otherColor.setColor(Color.BLUE);
        upperColor.setColor(Color.RED);
        lowerColor.setColor(Color.MAGENTA);
        painTotal.setCustomPaint(totalColor);
        painOther.setCustomPaint(otherColor);
        painUpper.setCustomPaint(upperColor);
        painLower.setCustomPaint(lowerColor);
        graph.addSeries(painTotal);
        graph.addSeries(painOther);
        graph.addSeries(painUpper);
        graph.addSeries(painLower);
        graph.getViewport().setMaxY(10);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMinX(0);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graph, menu);
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
    private void painScaleOpener() {
        Intent intent = new Intent(this, PainScaleActivity.class);
        startActivityForResult(intent, 1);
    }
    private void foodOpener() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void stoolRecordOpener() {
        Intent intent = new Intent(this, StoolRecordActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view oldItem clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_food_diary) {
            foodOpener();
        } else if (id == R.id.nav_new_pain) {
            painScaleOpener();
        } else if (id == R.id.nav_stool) {
            stoolRecordOpener();
        } else if (id == R.id.nav_stats) {
//            TODO make stats
        } else if (id == R.id.nav_graphing) {
            //graphModeOpener();
        } else if (id == R.id.nav_send) {
            //TODO export database
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
