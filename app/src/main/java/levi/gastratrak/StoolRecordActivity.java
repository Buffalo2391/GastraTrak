package levi.gastratrak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import java.sql.Time;

public class StoolRecordActivity extends AppCompatActivity {

    private final DatabaseController db = new DatabaseController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stool_record);
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
    private void saveButtonPressed() {
        SeekBar consistencySeeker = findViewById(R.id.consistencySeeker);
        SeekBar wetnessSeeker = findViewById(R.id.wetnessSeeker);
        SeekBar difficultySeeker = findViewById(R.id.difficultySeeker);
        int[] stoolArray = new int[] {consistencySeeker.getProgress(),wetnessSeeker.getProgress(),difficultySeeker.getProgress()};
        db.addStoolRecording(new StoolItem(stoolArray, new Time(System.currentTimeMillis())));
        finish();
    }
}
