package pl.olencki.jan.keyboardvirtuoso.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pl.olencki.jan.keyboardvirtuoso.R;

public class MainActivity extends AppCompatActivity {
    private Button buttonStartPhrasesMode;
    private Button buttonStartCharsMode;
    private Button buttonStatistics;
    private Button buttonSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartPhrasesMode = findViewById(R.id.btn_main_start_phrases);
        buttonStartCharsMode = findViewById(R.id.btn_main_start_chars);
        buttonStatistics = findViewById(R.id.btn_main_statistics);
        buttonSettings = findViewById(R.id.btn_main_settings);
        addEventListeners();
    }

    private void addEventListeners() {
        buttonStartPhrasesMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhraseGameActivity.class);
                startActivity(intent);
            }
        });

        buttonStartCharsMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CharGameActivity.class);
                startActivity(intent);
            }
        });

        buttonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
