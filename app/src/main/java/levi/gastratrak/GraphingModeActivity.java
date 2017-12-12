package levi.gastratrak;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class GraphingModeActivity extends AppCompatActivity {

    DatabaseController db = new DatabaseController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphing_mode);
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
}
