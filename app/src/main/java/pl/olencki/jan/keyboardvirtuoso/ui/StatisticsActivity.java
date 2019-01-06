package pl.olencki.jan.keyboardvirtuoso.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.R;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharGameStatistics;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharGameStatisticsDao;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.GamesDatabase;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.KeyboardDao;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.KeyboardData;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.PhraseGameStatistics;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.PhraseGameStatisticsDao;

public class StatisticsActivity extends AppCompatActivity {
    private Button buttonReturnToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        buttonReturnToMenu = findViewById(R.id.btn_statistics_return);
        addButtonsListeners();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                GamesDatabase db = GamesDatabase.getInstance(
                        StatisticsActivity.this.getApplicationContext());
                KeyboardDao keyboardDao = db.keyboardDao();
                CharGameStatisticsDao charGameStatisticsDao = db.charGameStatisticsDao();
                PhraseGameStatisticsDao phraseGameStatisticsDao = db.phraseGameStatisticsDao();

                Log.d("Statistics", "Stats quering runs");
                List<KeyboardData> keyboards = keyboardDao.findAll();
                for (KeyboardData keyboard : keyboards) {
                    Log.d("Statistics", keyboard.toString());

                    List<CharGameStatistics> charGameStatistics = charGameStatisticsDao.loadByKeyboardId(
                            keyboard.id);
                    for (CharGameStatistics stat : charGameStatistics) {
                        Log.d("Statistics", stat.toString());
                    }

                    PhraseGameStatistics phraseGameStatistics = phraseGameStatisticsDao.loadByKeyboardId(
                            keyboard.id);
                    Log.d("Statistics", phraseGameStatistics.toString());
                }
            }
        });


    }

    private void addButtonsListeners() {
        buttonReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMenu();
            }
        });
    }

    @Override
    public void onBackPressed() {
        returnToMenu();
    }

    private void returnToMenu() {
        Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
