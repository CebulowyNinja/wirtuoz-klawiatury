package pl.olencki.jan.keyboardvirtuoso.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import pl.olencki.jan.keyboardvirtuoso.ui.*;

public abstract class AppActivity extends AppCompatActivity {
    protected AppPreferences appPreferences;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appPreferences = new AppPreferences(this);
    }

    @Override
    public void onBackPressed() {
        returnToMenu();
    }

    protected void returnToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
