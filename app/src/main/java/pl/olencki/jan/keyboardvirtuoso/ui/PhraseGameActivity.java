package pl.olencki.jan.keyboardvirtuoso.ui;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import pl.olencki.jan.keyboardvirtuoso.*;
import pl.olencki.jan.keyboardvirtuoso.game.*;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.*;

public class PhraseGameActivity extends GameActivity {


    private TextView textViewGameInitial;
    private TextView textViewGameSummaryTop;
    private TextView textViewGameSummary;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_phrase_game;
    }

    @Override
    protected void initGame() {
        super.initGame();

        int challengesCount = appPreferences.getPhrasesCountSingleGame();

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
        setEnabledEditTextChallenge(true);

        PhraseChallenge challenge = (PhraseChallenge) game.getCurrentChallenge();
        String[] words = challenge.getPhrase().getWords();

        SpannableStringBuilder builder = new SpannableStringBuilder();
            ForegroundColorSpan span = new ForegroundColorSpan(
                    ContextCompat.getColor(this, R.color.color_text_challenge_default));
        for (String word : words) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.append(word + " ", span, 0);
            } else {
                int length = builder.length();
                builder.append(word + " ");
                builder.setSpan(span, length, length + word.length(), 0);
            }
        }

        textViewChallenge.setText(builder);
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


    protected void displayStatistics() {
        PhraseGame phraseGame = (PhraseGame) game;
        PhraseGameStatistics statistics = phraseGame.getGameStatistics();

        TextView textViewSummaryTop = findViewById(R.id.text_game_summary_top);
        TextView textViewSummary = findViewById(R.id.text_game_summary);

        String summaryText = getString(R.string.text_phrase_game_summary_count,
                                       statistics.correctWordsCount,
                                       statistics.wordsCount, statistics.elapsedTime);
        String summarySpeedText = getString(R.string.text_phrase_game_summary_speed,
                                            statistics.getWordsPerMinute());

        textViewSummaryTop.setText(summarySpeedText);
        textViewSummary.setText(summaryText);
    }

    @Override
    protected void initViewFields() {
        super.initViewFields();

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
                }
                return true;
            }
        });
    }
}
