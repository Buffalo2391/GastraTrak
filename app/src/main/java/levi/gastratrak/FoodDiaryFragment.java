package levi.gastratrak;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class FoodDiaryFragment extends Fragment{
    static final int MIN_DISTANCE = 150;
    private final ArrayList<FoodItem> foodsList = new ArrayList<>();
    private View view;
    private FoodItemAdapter foodAdapter;
    private OnFragmentInteractionListener mListener;
    private GestureDetectorCompat mDetector;

    public FoodDiaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.content_food, container, false);
        mDetector = new GestureDetectorCompat(this.getContext(), new MyGestureListener());
        return view;
    }
    public boolean onTouchEvent( MotionEvent event){
        this.mDetector.onTouchEvent(event);
        addFoodEmpty();
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        foodAdapter = new FoodItemAdapter(this.getContext(), foodsList, this);
        ListView foodDiaryView = this.view.findViewById(R.id.FoodListView);
        foodDiaryView.setAdapter(foodAdapter);
        FloatingActionButton fab = this.view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodEmpty();
            }
        });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar displayDate = Calendar.getInstance();
        displayDate.set(Calendar.HOUR_OF_DAY, 0);
        displayDate.set(Calendar.MINUTE, 0);
        displayDate.set(Calendar.SECOND, 0);
        this.foodAdapter.updateFromDatabase(displayDate);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void editFoodItem(FoodItem item) {
        Intent intent = new Intent(this.getContext(), FoodDiaryAddEditActivity.class);
        intent.putExtra("foodName", item.getFoodItem());
        intent.putExtra("foodTime", item.getFoodTime().getTime());
        intent.putExtra("isEdit", true);
        startActivity(intent);
    }

    private void addFoodEmpty() {
        Intent intent = new Intent(this.getContext(), FoodDiaryAddEditActivity.class);
        intent.putExtra("isEdit", false);
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: figure this out. Apparently I need it but idk
        void onFragmentInteraction(Uri uri);
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {

            return true;
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
            view.setBackgroundColor(Color.BLUE);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if (Math.abs(velocityX) > MIN_DISTANCE) {
                // Left to Right swipe action
                if (velocityX > 0) {

                    view.setBackgroundColor(Color.BLUE);
                }

                // Right to left swipe action
                else {
                    view.setBackgroundColor(Color.YELLOW);
                }
            }
            return true;
        }
    }
}