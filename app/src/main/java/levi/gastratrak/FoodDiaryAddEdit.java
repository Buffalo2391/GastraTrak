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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FoodDiaryAddEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary_add_edit);
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonPressed();
            }
        });
    }

    private void saveButtonPressed() {
        EditText foodName = findViewById(R.id.FoodNameInput);
        TimePicker foodTime = findViewById(R.id.timePicker);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR, foodTime.getHour());
        cal.set(Calendar.MINUTE, foodTime.getMinute());
        Intent output = new Intent();
        output.putExtra("foodName", foodName.getText().toString());
        output.putExtra("foodTime", cal.getTimeInMillis());
        setResult(RESULT_OK, output);
        finish();
    }
}
