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

public class foodItemAdapter extends ArrayAdapter<foodItem> {

    private ArrayList<foodItem> items;
    private int layoutResourceId;
    private Context context;
    private databaseController databaseControllerObject;
    public foodItemAdapter(Context context, int layoutResourceId, ArrayList<foodItem> items, databaseController databaseControlObject) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
        this.databaseControllerObject = databaseControlObject;
    }

    @Override
    public View getView(final int position, View row, ViewGroup parent) {

        foodItemHolder holder;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new foodItemHolder();
        holder.foodObject = items.get(position);
        holder.foodButton = row.findViewById(R.id.foodItemRemoveButton);
        holder.foodButton.setTag(holder.foodObject);

        holder.foodType = row.findViewById(R.id.foodItemName);
        holder.foodTime = row.findViewById(R.id.timeConsumed);
        row.setTag(holder);
        setupItem(holder);
        holder.foodButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                databaseControllerObject.removeFoodItem(items.get(position));
                items.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.foodType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                //remove old item
                databaseControllerObject.removeFoodItem(items.get(position));
                items.get(position).setFoodItem(editable.toString());
                databaseControllerObject.addFoodItem(items.get(position));


            }
        });
        holder.foodTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String str = charSequence.toString();
//                DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
//                Date date = null;
//                try {
//                    date = formatter.parse(str);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                java.sql.Time foodTime = new java.sql.Time(date.getTime());
//                items.get(position).setItemTime(foodTime);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return row;
    }


    private void setupItem(foodItemHolder holder) {
        holder.foodType.setText(holder.foodObject.getFoodItem());
        holder.foodTime.setText(String.valueOf(holder.foodObject.getFoodTime()));
    }

    public static class foodItemHolder {
        foodItem foodObject;
        TextView foodType;
        TextView foodTime;
        ImageButton foodButton;
    }
}