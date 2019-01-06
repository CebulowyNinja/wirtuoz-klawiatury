package pl.olencki.jan.keyboardvirtuoso.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pl.olencki.jan.keyboardvirtuoso.R;
import pl.olencki.jan.keyboardvirtuoso.app.AppActivity;

public class MainActivity extends AppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addEventListeners();
    }

    private void addEventListeners() {
        Button buttonStartPhrasesMode = findViewById(R.id.btn_main_start_phrases);
        buttonStartPhrasesMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhraseGameActivity.class);
                startActivity(intent);
            }
        });

        Button buttonStartCharsMode = findViewById(R.id.btn_main_start_chars);
        buttonStartCharsMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CharGameActivity.class);
                startActivity(intent);
            }
        });

        Button buttonStatistics = findViewById(R.id.btn_main_statistics);
        buttonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        Button buttonSettings = findViewById(R.id.btn_main_settings);
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
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(homeIntent);
    }
}
