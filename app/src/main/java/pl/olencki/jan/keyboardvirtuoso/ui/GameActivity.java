package pl.olencki.jan.keyboardvirtuoso.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.olencki.jan.keyboardvirtuoso.R;
import pl.olencki.jan.keyboardvirtuoso.game.Game;
import pl.olencki.jan.keyboardvirtuoso.game.exception.ChallengeTimerException;

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

    protected TextView textViewChallengeCount;

    abstract protected int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initViewFields();
        addEventListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initGame();
        displayCurrentChallenge();
    }

    protected void initGame() {
        showInitialScreen();
        gameStage = INITIAL_STAGE;
    }

    protected void startGame() {
        hideInitialScreen();
    }

    protected void onStartGame() {
        game.generateNewCurrentChallenge();
        startChallenge();
    }

    protected void startChallenge() {
        gameStage = CHALLENGE;
        displayCurrentChallenge();
        game.getCurrentChallenge().startMeasureTime();
    }

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

    protected void startSummary() {
        gameStage = SUMMARY;
        showSummaryScreen();
    }

    protected void displayCurrentChallenge() {
        String doneChallenges = game.getCurrentChallengeIndex() + 1 + "/";
        doneChallenges += game.getChallengesCount();

        textViewChallengeCount.setText(doneChallenges);
    }

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
    }

    protected void initViewFields() {
        initialLayout = findViewById(R.id.screen_game_initial);
        gameLayout = findViewById(R.id.screen_game);
        summaryLayout = findViewById(R.id.screen_game_summary);

        textViewChallengeCount = findViewById(R.id.text_game_challenges_count);
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
