package pl.olencki.jan.keyboardvirtuoso.ui;

import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.*;
import pl.olencki.jan.keyboardvirtuoso.game.*;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.*;

import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.*;

public class CharGameActivity extends GameActivity {


    private TextView textViewGameInitial;
    private TextView textViewGameSummaryTop;
    private TextView textViewGameSummary;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_char_game;
    }

    @Override
    protected void initGame() {
        super.initGame();

        CharType[] charTypes = CharType.values();
        int challengesCount = appPreferences.getCharsCountSingleGame();
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

    protected void displayStatistics() {
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

        textViewSummaryTop.setText(summarySpeedText);
        textViewSummary.setText(summaryText);
    }

    @Override
    protected void initViewFields() {
        super.initViewFields();

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
