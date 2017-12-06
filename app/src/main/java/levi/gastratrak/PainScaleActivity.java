package levi.gastratrak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.sql.Time;

public class PainScaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_scale);
        Button saveButton = findViewById(R.id.painSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonPressed();
            }
        });
    }

    private void saveButtonPressed() {
        SeekBar totalPainSeeker = findViewById(R.id.totalPainSeeker);
        SeekBar otherPainSeeker = findViewById(R.id.otherPainSeeker);
        SeekBar upperPainSeeker = findViewById(R.id.upperGutPainSeeker);
        SeekBar lowerPainSeeker = findViewById(R.id.lowerGutPainSeeker);
        int[] painArray = new int[] {totalPainSeeker.getProgress(),otherPainSeeker.getProgress(),upperPainSeeker.getProgress(),lowerPainSeeker.getProgress()};
        Intent output = new Intent();
        output.putExtra("PainArray", painArray);
        setResult(RESULT_OK, output);
        finish();
    }

}
