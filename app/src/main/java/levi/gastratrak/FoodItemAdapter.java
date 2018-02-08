package levi.gastratrak;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Levi on 17/11/2017. Adapter for food items on food diary
 */

class FoodItemAdapter extends ArrayAdapter<FoodItem> {

    private final ArrayList<FoodItem> items;
    private final int layoutResourceId;
    private final Context context;
    private final DatabaseController db = new DatabaseController(this.getContext());
    private Calendar displayDate = Calendar.getInstance();
    private FoodDiaryFragment fragItem;
    public FoodItemAdapter(Context context, ArrayList<FoodItem> items, FoodDiaryFragment fragItem) {
        super(context, R.layout.food_diary_entry, items);
        this.layoutResourceId = R.layout.food_diary_entry;
        this.context = context;
        this.items = items;
        this.fragItem = fragItem;
        setDisplayDate(Calendar.getInstance());

    }

    @NonNull
    @Override
    public View getView(final int position, View row, @NonNull final ViewGroup parent) {

        foodItemHolder holder;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new foodItemHolder();
        holder.foodObject = items.get(position);
        holder.foodButton = row.findViewById(R.id.foodItemEditButton);
        holder.foodButton.setTag(holder.foodObject);

        holder.foodType = row.findViewById(R.id.foodItemName);
        holder.foodTime = row.findViewById(R.id.timeConsumed);
        row.setTag(holder);
        setupItem(holder);
        holder.foodButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fragItem.editFoodItem(items.get(position));
            }
        });
        return row;
    }

    private void setDisplayDate(Calendar calendar){
        displayDate = (Calendar) calendar.clone();
        displayDate.set(Calendar.HOUR_OF_DAY, 0);
        displayDate.set(Calendar.MINUTE, 0);
        displayDate.set(Calendar.SECOND, 0);
        updateFromDatabase(displayDate);
    }

    private void setupItem(foodItemHolder holder) {
        Calendar time = holder.foodObject.getFoodTime();
        holder.foodType.setText(holder.foodObject.getFoodItem());
        holder.foodTime.setText(String.format("%02d:%02d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE)));
    }
    public void updateFromDatabase(Calendar startTime){
        items.clear();
        Calendar endDate = (Calendar)startTime.clone();
        endDate.setTimeInMillis(startTime.getTimeInMillis()+86400000);
        items.addAll(db.getDateFoodItems(startTime,endDate));
        notifyDataSetChanged();
    }

    public static class foodItemHolder {
        FoodItem foodObject;
        TextView foodType;
        TextView foodTime;
        ImageButton foodButton;
    }
}