package levi.gastratrak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class StoolRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stool_record);
        Button saveButton = findViewById(R.id.saveStoolButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonPressed();
            }
        });
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
