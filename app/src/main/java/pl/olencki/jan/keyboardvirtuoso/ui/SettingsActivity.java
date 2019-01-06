package pl.olencki.jan.keyboardvirtuoso.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.olencki.jan.keyboardvirtuoso.*;

public class SettingsActivity extends AppActivity {
    private EditText editTextPhrasesCount;
    private EditText editTextCharsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViewFields();
        addEventListeners();
    }

    private void initViewFields() {
        editTextPhrasesCount = findViewById(R.id.edit_text_settings_phrases_count);
        String phrasesCount = String.valueOf(appPreferences.getPhrasesCountSingleGame());
        editTextPhrasesCount.setText(phrasesCount);


        editTextCharsCount = findViewById(R.id.edit_text_settings_chars_count);
        String charsCount = String.valueOf(appPreferences.getCharsCountSingleGame());
        editTextCharsCount.setText(charsCount);

    }

    private void addEventListeners() {
        editTextPhrasesCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    EditText editText = (EditText) view;
                    int value = Integer.parseInt(editText.getText().toString());
                    value = appPreferences.validatePhraseCountSingleGame(value);

                    editText.setText(String.valueOf(value));
                }
            }
        });

        editTextCharsCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    EditText editText = (EditText) view;
                    int value = Integer.parseInt(editText.getText().toString());
                    value = appPreferences.validateCharseCountSingleGame(value);

                    editText.setText(String.valueOf(value));
                }
            }
        });

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
                returnToMenu();
            }
        });
    }

    private void saveSettings() {
        int phrasesCount = Integer.parseInt(editTextPhrasesCount.getText().toString());
        appPreferences.setPhrasesCountSingleGame(phrasesCount);

        int charsCount = Integer.parseInt(editTextCharsCount.getText().toString());
        appPreferences.setCharsCountSingleGame(charsCount);
    }
}
