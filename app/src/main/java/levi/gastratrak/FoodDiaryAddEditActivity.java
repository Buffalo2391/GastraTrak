package levi.gastratrak;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FoodDiaryAddEditActivity extends AppCompatActivity {
    FoodItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary_add_edit);
        Button saveButton = findViewById(R.id.SaveButton);
        Button deleteButton = findViewById(R.id.DeleteButton);
        EditText foodName = findViewById(R.id.FoodNameInput);
        TimePicker foodTime = findViewById(R.id.timePicker);
        Intent intent = getIntent();
        String oldItemName = intent.getStringExtra("foodName");
        long oldItemTime = intent.getLongExtra("foodTime", 0);
        item = new FoodItem(oldItemName, new Time(oldItemTime));
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(oldItemTime);
        foodTime.setIs24HourView(Boolean.TRUE);
        foodName.setText(oldItemName);
        foodTime.setHour(cal.get(Calendar.HOUR_OF_DAY));
        foodTime.setMinute(cal.get(Calendar.MINUTE));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonPressed();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButtonPressed();
            }
        });
    }

    private  void deleteButtonPressed() {
        Intent output = new Intent();
        output.putExtra("foodName", item.getFoodItem());
        output.putExtra("foodTime", item.getFoodTime().getTime());
        setResult(RESULT_CANCELED, output);
        finish();
    }
    private void saveButtonPressed() {
        EditText foodName = findViewById(R.id.FoodNameInput);
        TimePicker foodTime = findViewById(R.id.timePicker);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, foodTime.getHour());
        cal.set(Calendar.MINUTE, foodTime.getMinute());
        cal.set(Calendar.SECOND, 0);
        Intent output = new Intent();
        output.putExtra("foodName", foodName.getText().toString());
        output.putExtra("foodTime", cal.getTimeInMillis());
        setResult(RESULT_OK, output);
        finish();
    }
}
