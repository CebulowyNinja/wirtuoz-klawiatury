package pl.olencki.jan.keyboardvirtuoso.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.R;
import pl.olencki.jan.keyboardvirtuoso.game.Game;
import pl.olencki.jan.keyboardvirtuoso.game.exception.ChallengeTimerException;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.KeyboardData;

import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.CHALLENGE;
import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.CHALLENGE_CORRECT;
import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.CHALLENGE_INCORRECT;
import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.INITIAL_STAGE;
import static pl.olencki.jan.keyboardvirtuoso.ui.GameStage.SUMMARY;

public abstract class GameActivity extends AppCompatActivity {
    protected GameStage gameStage;
    protected Game game;

    protected ConstraintLayout initialLayout;
    protected ConstraintLayout gameLayout;
    protected ConstraintLayout summaryLayout;

    protected TextView textViewChallenge;
    protected EditText editTextChallenge;
    protected EditText editTextHidden;
    protected TextView textViewChallengeCount;

    protected KeyboardData keyboardData;

    abstract protected int getLayoutId();

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initViewFields();
        addEventListeners();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();

        initGame();
        displayCurrentChallenge();
    }

    @CallSuper
    protected void initGame() {
        showInitialScreen();
        gameStage = INITIAL_STAGE;
    }

    @CallSuper
    protected void startGame() {
        hideInitialScreen();
    }

    @CallSuper
    protected void onStartGame() {
        game.generateNewCurrentChallenge();
        startChallenge();
    }

    @CallSuper
    protected void startChallenge() {
        gameStage = CHALLENGE;
        game.getCurrentChallenge().startMeasureTime();

        displayCurrentChallenge();

        String doneChallenges = game.getCurrentChallengeIndex() + 1 + "/";
        doneChallenges += game.getChallengesCount();

        textViewChallengeCount.setText(doneChallenges);
    }

    @CallSuper
    protected void onChallengeComplete() {
        try {
            game.getCurrentChallenge().stopMeasureTime();
        } catch (ChallengeTimerException e) {
            Log.e("GameActivity", e.toString());
        }

        if (game.getCurrentChallenge().isCorrect()) {
            gameStage = CHALLENGE_CORRECT;
        } else {
            gameStage = CHALLENGE_INCORRECT;
        }
    }

    protected void nextChallenge() {
        if (game.hasNextChallenge()) {
            gameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    game.nextChallenge();
                    startChallenge();
                }
            }, getResources().getInteger(R.integer.delay_new_challenge));
        } else {
            startSummary();
        }
    }

    @CallSuper
    protected void startSummary() {
        gameStage = SUMMARY;
        showSummaryScreen();

        hideKeyboard();

        displayStatistics();
        game.addToDatabase(this.getApplicationContext(), keyboardData);
    }

    @CallSuper
    protected void initViewFields() {
        initialLayout = findViewById(R.id.screen_game_initial);
        gameLayout = findViewById(R.id.screen_game);
        summaryLayout = findViewById(R.id.screen_game_summary);

        textViewChallenge = findViewById(R.id.text_game_challenge);
        editTextChallenge = findViewById(R.id.edit_text_game_input);
        editTextHidden = findViewById(R.id.edit_text_game_hiddden);

        textViewChallengeCount = findViewById(R.id.text_game_challenges_count);
    }

    @CallSuper
    protected void addEventListeners() {
        Button buttonStartGame = findViewById(R.id.btn_game_start);
        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameStage == INITIAL_STAGE) {
                    startGame();
                }
            }
        });

        Button buttonExit = findViewById(R.id.btn_game_exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameStage == CHALLENGE || gameStage == CHALLENGE_CORRECT || gameStage == CHALLENGE_INCORRECT) {
                    GameActivity.this.returnToMenu();
                }
            }
        });

        Button buttonRestartGame = findViewById(R.id.btn_game_start_again);
        buttonRestartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

        Button buttonReturnToMenu = findViewById(R.id.btn_game_return_to_menu);
        buttonReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMenu();
            }
        });

        Button buttonGoToStats = findViewById(R.id.btn_game_statistics);
        buttonGoToStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        editTextChallenge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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

        editTextHidden.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return true;
            }
        });
    }

    abstract protected void displayStatistics();

    abstract protected void displayCurrentChallenge();

    protected void onHideInitialScreen() {
        onStartGame();
    }

    protected void onShowSummaryScreen() {
    }

    protected void showInitialScreen() {
        initialLayout.setVisibility(View.VISIBLE);
        initialLayout.setAlpha(1.0f);

        gameLayout.setVisibility(View.VISIBLE);
        gameLayout.setAlpha(1.0f);

        summaryLayout.setVisibility(View.GONE);
        summaryLayout.setAlpha(1.0f);
    }

    protected void hideInitialScreen() {
        int duration = getResources().getInteger(R.integer.animation_time_short);
        initialLayout.animate().alpha(0.0f).setDuration(duration).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        initialLayout.setVisibility(View.GONE);
                        onHideInitialScreen();
                    }
                });
    }

    protected void showSummaryScreen() {
        int duration = getResources().getInteger(R.integer.animation_time_long);

        summaryLayout.setAlpha(0.0f);
        summaryLayout.setVisibility(View.VISIBLE);
        summaryLayout.animate().alpha(1.0f).setDuration(duration).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        onShowSummaryScreen();
                    }
                });
    }

    protected KeyboardData getCurrentKeyboard() {
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

    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);

        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        view.clearFocus();
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void setEnabledEditTextChallenge(boolean enable) {
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

    protected void returnToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void restartGame() {
        if (gameStage != INITIAL_STAGE) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
}
