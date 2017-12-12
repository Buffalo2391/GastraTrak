package levi.gastratrak;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

/**
 * Created by Levi on 17/11/2017. Adapter for food items on food diary
 */

public class FoodItemAdapter extends ArrayAdapter<FoodItem> {

    private ArrayList<FoodItem> items;
    private int layoutResourceId;
    private Context context;
    private DatabaseController db = new DatabaseController(this.getContext());
    public FoodItemAdapter(Context context, int layoutResourceId, ArrayList<FoodItem> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
        updateFromDatabase();
    }

    @Override
    public View getView(final int position, View row, final ViewGroup parent) {

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
                ((MainActivity) context).editFoodItem(items.get(position));
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