package pl.olencki.jan.keyboardvirtuoso.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pl.olencki.jan.keyboardvirtuoso.R;

public class SettingsActivity extends AppCompatActivity {
    private Button buttonReturnToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonReturnToMenu = findViewById(R.id.btn_settings_return);
        addButtonsListeners();
    }

    private void addButtonsListeners() {
        buttonReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.returnToMenu();
            }
        });
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
