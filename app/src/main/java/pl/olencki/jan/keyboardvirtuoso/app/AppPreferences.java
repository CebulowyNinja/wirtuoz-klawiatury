package pl.olencki.jan.keyboardvirtuoso.app;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private SharedPreferences preferences;

    public AppPreferences(Context context) {
        this.preferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
    }

    public int getPhrasesCountSingleGame() {
        return preferences.getInt("phrases_count_single_game", 10);
    }

    public void setPhrasesCountSingleGame(int value) {
        value = validatePhraseCountSingleGame(value);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("phrases_count_single_game", value).apply();
    }

    public int validatePhraseCountSingleGame(int value) {
        if (value < 1 || value > 100) {
            return 10;
        }

        return value;
    }

    public int getCharsCountSingleGame() {
        return preferences.getInt("chars_count_single_game", 10);
    }

    public void setCharsCountSingleGame(int value) {
        value = validateCharsCountSingleGame(value);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("chars_count_single_game", value).apply();
    }

    public int validateCharsCountSingleGame(int value) {
        if (value < 1 || value > 100) {
            return 10;
        }

        return value;
    }
}
