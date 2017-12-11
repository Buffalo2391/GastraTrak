package levi.gastratrak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class StoolRecordActivity extends AppCompatActivity {

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
        Intent output = new Intent();
        output.putExtra("StoolArray", stoolArray);
        setResult(RESULT_OK, output);
        finish();
    }
}
