package levi.gastratrak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;

public class FoodDiaryAddEditActivity extends AppCompatActivity {
    FoodItem oldItem;
    DatabaseController db = new DatabaseController(this);
    boolean isEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary_add_edit);
        Button deleteButton = findViewById(R.id.DeleteButton);
        EditText foodName = findViewById(R.id.FoodNameInput);
        TimePicker foodTime = findViewById(R.id.timePicker);
        Intent intent = getIntent();
        String oldItemName = intent.getStringExtra("foodName");
        long oldItemTime = intent.getLongExtra("foodTime", System.currentTimeMillis());
        this.isEdit = intent.getBooleanExtra("isEdit", true);
        oldItem = new FoodItem(oldItemName, new Time(oldItemTime));
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(oldItemTime);
        foodTime.setIs24HourView(Boolean.TRUE);
        foodName.setText(oldItemName);
        foodTime.setHour(cal.get(Calendar.HOUR_OF_DAY));
        foodTime.setMinute(cal.get(Calendar.MINUTE));
        if (isEdit) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteButtonPressed();
                }
            });
        } else {
            deleteButton.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.return_to_main_saved, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_accept:
                saveButtonPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    private  void deleteButtonPressed() {
        db.removeFoodItem(this.oldItem);
        setResult(RESULT_CANCELED);
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
        FoodItem item = new FoodItem(foodName.getText().toString(), new Time(cal.getTimeInMillis()));
        if(isEdit){
            db.removeFoodItem(this.oldItem);
        }
        db.addFoodItem(item);
        setResult(RESULT_OK);
        finish();
    }
}
