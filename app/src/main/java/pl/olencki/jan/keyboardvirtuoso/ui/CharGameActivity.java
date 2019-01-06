package pl.olencki.jan.keyboardvirtuoso.ui;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.R;
import pl.olencki.jan.keyboardvirtuoso.game.CharChallenge;
import pl.olencki.jan.keyboardvirtuoso.game.CharGame;
import pl.olencki.jan.keyboardvirtuoso.game.CharType;
import pl.olencki.jan.keyboardvirtuoso.game.CharWithType;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharGameStatistics;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.KeyboardData;

import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.CHALLENGE;
import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.CHALLENGE_CORRECT;

public class CharGameActivity extends GameActivity {
    private TextView textViewChallenge;
    private EditText editTextChallenge;
    private EditText editTextHidden;

    private TextView textViewGameInitial;
    private TextView textViewGameSummaryTop;
    private TextView textViewGameSummary;
    private KeyboardData keyboardData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_char_game;
    }

    @Override
    protected void initGame() {
        super.initGame();

        CharType[] charTypes = CharType.values();
        int challengesCount = 5;
        game = new CharGame(charTypes, challengesCount);

        String gameInitialText = getString(R.string.text_char_game_initial_desc, challengesCount);
        Spanned gameInitialSpanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            gameInitialSpanned = Html.fromHtml(gameInitialText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            gameInitialSpanned = Html.fromHtml(gameInitialText);
        }
        textViewGameInitial.setText(gameInitialSpanned);

        hideKeyboard();
    }

    @Override
    protected void displayCurrentChallenge() {
        super.displayCurrentChallenge();
        setEnabledEditTextChallenge(true);

        CharChallenge challenge = (CharChallenge) game.getCurrentChallenge();
        char challengeChar = challenge.getCharWithType().getChar();
        textViewChallenge.setText(Character.toString(challengeChar));
        textViewChallenge.setTextColor(
                getResources().getColor(R.color.color_text_challenge_default));
    }

    @Override
    protected void onChallengeComplete() {
        super.onChallengeComplete();

        setEnabledEditTextChallenge(false);

        if (gameStage == CHALLENGE_CORRECT) {
            textViewChallenge.setTextColor(
                    getResources().getColor(R.color.color_text_challenge_correct));
        } else {
            textViewChallenge.setTextColor(
                    getResources().getColor(R.color.color_text_challenge_incorrect));
        }
    }

    @Override
    protected void startSummary() {
        super.startSummary();
        hideKeyboard();

        showStats();
        game.addToDatabase(this.getApplicationContext(), keyboardData);
    }

    private void showStats() {
        CharGame charGame = (CharGame) game;
        List<CharGameStatistics> stats = charGame.getGameStatistics();

        int charsCount = 0;
        int correctCharsCount = 0;
        float ellapsedTime = 0;
        for (CharGameStatistics stat : stats) {
            charsCount += stat.charsCount;
            correctCharsCount += stat.correctCharsCount;
            ellapsedTime += stat.elapsedTime;
        }

        float speed = charsCount / ellapsedTime * 60;

        TextView textViewSummaryTop = findViewById(R.id.text_game_summary_top);
        TextView textViewSummary = findViewById(R.id.text_game_summary);

        String summaryText = getString(R.string.text_char_game_summary_count, correctCharsCount,
                                       charsCount, ellapsedTime);
        String summarySpeedText = getString(R.string.text_char_game_summary_speed, speed);

        textViewSummaryTop.setText(summaryText);
        textViewSummary.setText(summarySpeedText);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);

        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        view.clearFocus();
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setEnabledEditTextChallenge(boolean enable) {
        if (enable) {
            editTextChallenge.getText().clear();
            editTextChallenge.setEnabled(true);
            editTextChallenge.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editTextChallenge, InputMethodManager.SHOW_IMPLICIT);
            editTextChallenge.setCursorVisible(false);
        } else {
            editTextHidden.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editTextHidden, InputMethodManager.SHOW_IMPLICIT);
            editTextChallenge.setEnabled(false);
            editTextHidden.setCursorVisible(false);
            editTextChallenge.setCursorVisible(false);
        }
    }

    private KeyboardData getCurrentKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> inputMethodInfos = inputMethodManager.getEnabledInputMethodList();
        String currentInputMethodId = Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.DEFAULT_INPUT_METHOD
        );

        for (InputMethodInfo info : inputMethodInfos) {
            if (info.getId().equals(currentInputMethodId)) {
                return new KeyboardData(null, info.loadLabel(getPackageManager()).toString(),
                                        info.getId());
            }
        }

        return null;
    }

    @Override
    protected void initViewFields() {
        super.initViewFields();

        textViewChallenge = findViewById(R.id.text_game_challenge);
        editTextChallenge = findViewById(R.id.edit_text_game_input);
        editTextHidden = findViewById(R.id.edit_text_game_hiddden);

        TextView textViewChallengeCountDesc = findViewById(R.id.text_game_challenges_desc);
        textViewChallengeCountDesc.setText(R.string.text_chars_count_desc);

        textViewGameInitial = findViewById(R.id.text_game_initial);
        textViewGameSummaryTop = findViewById(R.id.text_game_summary_top);
        textViewGameSummary = findViewById(R.id.text_game_summary);
    }

    @Override
    protected void addEventListeners() {
        super.addEventListeners();

        editTextChallenge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (gameStage != CHALLENGE) {
                    return;
                }

                CharChallenge challenge = (CharChallenge) game.getCurrentChallenge();
                if (charSequence.length() > 0) {
                    challenge.setTypedCharWithType(new CharWithType(charSequence.charAt(0)));
                    onChallengeComplete();
                    nextChallenge();
                }

                if (keyboardData == null) {
                    keyboardData = getCurrentKeyboard();
                }
                if (!keyboardData.equals(getCurrentKeyboard())) {
                    restartGame();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 1) {
                    editable.delete(1, editable.length());
                }
            }
        });

    }
}
