package pl.olencki.jan.keyboardvirtuoso.ui;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.R;
import pl.olencki.jan.keyboardvirtuoso.game.Phrase;
import pl.olencki.jan.keyboardvirtuoso.game.PhraseChallenge;
import pl.olencki.jan.keyboardvirtuoso.game.PhraseGame;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.KeyboardData;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.PhraseGameStatistics;

import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.CHALLENGE;

public class PhraseGameActivity extends GameActivity {
    private TextView textViewChallenge;
    private EditText editTextChallenge;
    private EditText editTextHidden;

    private TextView textViewGameInitial;
    private TextView textViewGameSummaryTop;
    private TextView textViewGameSummary;
    private KeyboardData keyboardData;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_phrase_game;
    }

    @Override
    protected void initGame() {
        super.initGame();

        int challengesCount = 5;

        game = new PhraseGame(getResources().getStringArray(R.array.text_challenge_phrases),
                              challengesCount);

        String gameInitialText = getString(R.string.text_phrase_game_initial_desc, challengesCount);
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

        PhraseChallenge challenge = (PhraseChallenge) game.getCurrentChallenge();
        String challengeText = challenge.getPhrase().getText();
        textViewChallenge.setText(challengeText);
        textViewChallenge.setTextColor(
                getResources().getColor(R.color.color_text_challenge_default));
    }

    @Override
    protected void onChallengeComplete() {
        super.onChallengeComplete();

        setEnabledEditTextChallenge(false);

        PhraseChallenge phraseChallenge = (PhraseChallenge) game.getCurrentChallenge();
        String[] words = phraseChallenge.getPhrase().getWords();
        boolean[] areCorrect = phraseChallenge.areWordsCorrect();
        SpannableStringBuilder builder = new SpannableStringBuilder();

        for (int i = 0; i < words.length; i++) {
            ForegroundColorSpan span;
            if (areCorrect[i]) {
                span = new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.color_text_challenge_correct));
            } else {
                span = new ForegroundColorSpan(
                        ContextCompat.getColor(this, R.color.color_text_challenge_incorrect));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.append(words[i] + " ", span, 0);
            } else {
                int length = builder.length();
                builder.append(words[i] + " ");
                builder.setSpan(span, length, length + words[i].length(), 0);
            }
        }

        textViewChallenge.setText(builder);
    }

    protected void nextChallenge() {
        if (game.hasNextChallenge()) {
            gameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    game.nextChallenge();
                    startChallenge();
                }
            }, getResources().getInteger(R.integer.delay_new_phrase_challenge));
        } else {
            startSummary();
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
        PhraseGame phraseGame = (PhraseGame) game;
        PhraseGameStatistics statistics = phraseGame.getGameStatistics();

        TextView textViewSummaryTop = findViewById(R.id.text_game_summary_top);
        TextView textViewSummary = findViewById(R.id.text_game_summary);

        String summaryText = getString(R.string.text_phrase_game_summary_count,
                                       statistics.correctWordsCount,
                                       statistics.wordsCount, statistics.elapsedTime);
        String summarySpeedText = getString(R.string.text_phrase_game_summary_speed,
                                            statistics.getWordsPerMinute());

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
        } else {
            editTextHidden.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editTextHidden, InputMethodManager.SHOW_IMPLICIT);
            editTextChallenge.setEnabled(false);
            editTextHidden.setCursorVisible(false);
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
        editTextChallenge.setCursorVisible(true);
        editTextHidden = findViewById(R.id.edit_text_game_hiddden);

        TextView textViewChallengeCountDesc = findViewById(R.id.text_game_challenges_desc);
        textViewChallengeCountDesc.setText(R.string.text_phrases_count_desc);

        textViewGameInitial = findViewById(R.id.text_game_initial);
        textViewGameSummaryTop = findViewById(R.id.text_game_summary_top);
        textViewGameSummary = findViewById(R.id.text_game_summary);
    }

    @Override
    protected void addEventListeners() {
        super.addEventListeners();


        editTextChallenge.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    PhraseChallenge phraseChallenge = (PhraseChallenge) game.getCurrentChallenge();
                    phraseChallenge.setTypedPhrase(
                            new Phrase(editTextChallenge.getText().toString()));
                    onChallengeComplete();
                    nextChallenge();
                    return true;
                }
                return false;
            }
        });
        editTextHidden.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return true;
            }
        });

        editTextChallenge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (gameStage != CHALLENGE) {
                    return;
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
            }
        });

    }
}
