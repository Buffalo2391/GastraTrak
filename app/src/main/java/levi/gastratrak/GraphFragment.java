package levi.gastratrak;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;


public class GraphFragment extends Fragment {

    private DatabaseController db;
    private View view;
    private OnFragmentInteractionListener mListener;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.content_graph, container, false);

        return view;

    }

    public void graphAddEditOpener() {
        Intent intent = new Intent(this.getContext(), GraphAddEditActivity.class);
        startActivity(intent);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button bt = this.view.findViewById(R.id.graphButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphAddEditOpener();
            }
        });
        GraphView graph = view.findViewById(R.id.graph);
        ArrayList<PainItem> painArray = new ArrayList<>();
        painArray.addAll(db.getAllPainItems());
        DataPoint[] totalPainValues = new DataPoint[painArray.size()];
        DataPoint[] otherPainValues = new DataPoint[painArray.size()];
        DataPoint[] upperPainValues = new DataPoint[painArray.size()];
        DataPoint[] lowerPainValues = new DataPoint[painArray.size()];
        for (int i = 0; i < painArray.size(); i++) {
            totalPainValues[i] = new DataPoint(i + 1, painArray.get(i).getPainLevel()[0]);
            otherPainValues[i] = new DataPoint(i + 1, painArray.get(i).getPainLevel()[1]);
            upperPainValues[i] = new DataPoint(i + 1, painArray.get(i).getPainLevel()[2]);
            lowerPainValues[i] = new DataPoint(i + 1, painArray.get(i).getPainLevel()[3]);
        }
        BarGraphSeries<DataPoint> painTotal = new BarGraphSeries<>(totalPainValues);
        BarGraphSeries<DataPoint> painOther = new BarGraphSeries<>(otherPainValues);
        BarGraphSeries<DataPoint> painUpper = new BarGraphSeries<>(upperPainValues);
        BarGraphSeries<DataPoint> painLower = new BarGraphSeries<>(lowerPainValues);
        Paint totalColor = new Paint();
        Paint otherColor = new Paint();
        Paint upperColor = new Paint();
        Paint lowerColor = new Paint();
        totalColor.setColor(Color.YELLOW);
        otherColor.setColor(Color.BLUE);
        upperColor.setColor(Color.RED);
        lowerColor.setColor(Color.BLACK);
        painTotal.setCustomPaint(totalColor);
        painOther.setCustomPaint(otherColor);
        painUpper.setCustomPaint(upperColor);
        painLower.setCustomPaint(lowerColor);
        graph.addSeries(painTotal);
        graph.addSeries(painOther);
        graph.addSeries(painUpper);
        graph.addSeries(painLower);
        graph.getViewport().setMaxY(10);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMinX(0);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = new DatabaseController(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: figure it out eh
        void onFragmentInteraction(Uri uri);
    }
}