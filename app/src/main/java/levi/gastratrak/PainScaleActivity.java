package levi.gastratrak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import java.sql.Time;

public class PainScaleActivity extends AppCompatActivity {

    private final DatabaseController db = new DatabaseController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_scale);
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
        SeekBar totalPainSeeker = findViewById(R.id.totalPainSeeker);
        SeekBar otherPainSeeker = findViewById(R.id.otherPainSeeker);
        SeekBar upperPainSeeker = findViewById(R.id.upperGutPainSeeker);
        SeekBar lowerPainSeeker = findViewById(R.id.lowerGutPainSeeker);
        int[] painArray = new int[] {totalPainSeeker.getProgress(),otherPainSeeker.getProgress(),upperPainSeeker.getProgress(),lowerPainSeeker.getProgress()};
        db.addPainRecording(new PainItem(painArray, new Time(System.currentTimeMillis())));
        finish();
    }

}
