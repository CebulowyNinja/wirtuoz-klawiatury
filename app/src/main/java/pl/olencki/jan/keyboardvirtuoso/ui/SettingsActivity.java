package pl.olencki.jan.keyboardvirtuoso.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pl.olencki.jan.keyboardvirtuoso.R;

public class SettingsActivity extends AppCompatActivity {
    private EditText editTextPhrasesCount;
    private EditText editTextCharsCount;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences("app_preferences", MODE_PRIVATE);

        initViewFields();
        addEventListeners();
    }

    private void initViewFields() {
        editTextPhrasesCount = findViewById(R.id.edit_text_settings_phrases_count);
        String phrasesCount = String.valueOf(preferences.getInt("phrases_count_single_game", 10));
        editTextPhrasesCount.setText(phrasesCount);


        editTextCharsCount = findViewById(R.id.edit_text_settings_chars_count);
        String charsCount = String.valueOf(preferences.getInt("chars_count_single_game", 10));
        editTextCharsCount.setText(charsCount);

    }

    private void addEventListeners() {
        View.OnFocusChangeListener validateValueListener = new View.OnFocusChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    EditText editText = (EditText) view;
                    int value = Integer.parseInt(editText.getText().toString());
                    if(value < 1 || value > 100) {
                        editText.setText("10");
                    }
                }
            }
        };
        editTextPhrasesCount.setOnFocusChangeListener(validateValueListener);
        editTextCharsCount.setOnFocusChangeListener(validateValueListener);

        Button buttonSave = findViewById(R.id.btn_settings_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
                returnToMenu();
            }
        });

        Button buttonReturnToMenu = findViewById(R.id.btn_settings_return);
        buttonReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.returnToMenu();
            }
        });
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = preferences.edit();

        int phrasesCount = Integer.parseInt(editTextPhrasesCount.getText().toString());
        editor.putInt("phrases_count_single_game", phrasesCount);

        int charsCount = Integer.parseInt(editTextCharsCount.getText().toString());
        editor.putInt("chars_count_single_game", charsCount);

        editor.apply();
    }

    @Override
    public void onBackPressed() {
        returnToMenu();
    }

    private void returnToMenu() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
