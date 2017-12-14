package levi.gastratrak;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

/**
 * Created by Levi on 17/11/2017. Adapter for food items on food diary
 */

class FoodItemAdapter extends ArrayAdapter<FoodItem> {

    private final ArrayList<FoodItem> items;
    private final int layoutResourceId;
    private final Context context;
    private final DatabaseController db = new DatabaseController(this.getContext());
    private FoodDiaryFragment fragItem;
    public FoodItemAdapter(Context context, ArrayList<FoodItem> items, FoodDiaryFragment fragItem) {
        super(context, R.layout.food_diary_entry, items);
        this.layoutResourceId = R.layout.food_diary_entry;
        this.context = context;
        this.items = items;
        this.fragItem = fragItem;
        updateFromDatabase();
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


    private void setupItem(foodItemHolder holder) {
        holder.foodType.setText(holder.foodObject.getFoodItem());
        holder.foodTime.setText(String.valueOf(holder.foodObject.getFoodTime()));
    }
    public void updateFromDatabase(){
        items.clear();
        items.addAll(db.getAllFoodItems());
        notifyDataSetChanged();
    }

    public static class foodItemHolder {
        FoodItem foodObject;
        TextView foodType;
        TextView foodTime;
        ImageButton foodButton;
    }
}