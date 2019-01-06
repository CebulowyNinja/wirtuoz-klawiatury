package pl.olencki.jan.keyboardvirtuoso.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.R;
import pl.olencki.jan.keyboardvirtuoso.game.CharType;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharGameStatistics;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharGameStatisticsDao;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.GamesDatabase;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.KeyboardDao;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.KeyboardData;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.PhraseGameStatistics;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.PhraseGameStatisticsDao;

public class StatisticsActivity extends AppCompatActivity {
    ListView listViewStatitics;
    CombinedStatisticsAdapter statisticsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViewFields();
        addButtonsListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new LoadCombinedStatisticsAsyncTask().execute((Void) null);
    }

    private void initViewFields() {

        statisticsAdapter = new CombinedStatisticsAdapter(this,
                                                          R.layout.content_statistics_list_item);

        listViewStatitics = findViewById(R.id.list_statistics);
        listViewStatitics.setAdapter(statisticsAdapter);
    }

    private void addButtonsListeners() {
        Button buttonReturnToMenu = findViewById(R.id.btn_statistics_return);
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

    private class LoadCombinedStatisticsAsyncTask extends AsyncTask<Void, Void, List<CombinedStatistics>> {
        @Override
        protected List<CombinedStatistics> doInBackground(Void... voids) {
            GamesDatabase db = GamesDatabase.getInstance(
                    StatisticsActivity.this.getApplicationContext());

            KeyboardDao keyboardDao = db.keyboardDao();
            CharGameStatisticsDao charGameStatisticsDao = db.charGameStatisticsDao();
            PhraseGameStatisticsDao phraseGameStatisticsDao = db.phraseGameStatisticsDao();

            List<KeyboardData> keyboards = keyboardDao.findAll();
            List<CombinedStatistics> combinedStatisticsList = new ArrayList<>();
            for (KeyboardData keyboard : keyboards) {
                List<CharGameStatistics> charGameStatistics = charGameStatisticsDao.loadByKeyboardId(
                        keyboard.id);

                PhraseGameStatistics phraseGameStatistics = phraseGameStatisticsDao.loadByKeyboardId(
                        keyboard.id);

                CombinedStatistics combinedStatistics = new CombinedStatistics(keyboard,
                                                                               phraseGameStatistics,
                                                                               charGameStatistics);
                combinedStatisticsList.add(combinedStatistics);
            }

            return combinedStatisticsList;
        }

        @Override
        protected void onPostExecute(List<CombinedStatistics> statsList) {
            Log.d("stats", statsList.size() + "");
            statisticsAdapter.addAll(statsList);
        }
    }

    private class deleteKeyboardAsyncTask extends AsyncTask<CombinedStatistics, Void, CombinedStatistics[]> {
        @Override
        protected CombinedStatistics[] doInBackground(CombinedStatistics... statsArray) {
            GamesDatabase db = GamesDatabase.getInstance(
                    StatisticsActivity.this.getApplicationContext());
            KeyboardDao keyboardDao = db.keyboardDao();

            for (CombinedStatistics stat : statsArray) {
                keyboardDao.delete(stat.getKeyboardData());
            }

            return statsArray;
        }

        @Override
        protected void onPostExecute(CombinedStatistics[] statsList) {
            for (CombinedStatistics stats : statsList) {
                statisticsAdapter.remove(stats);
            }
        }
    }

    private class CombinedStatisticsAdapter extends ArrayAdapter<CombinedStatistics> {
        public CombinedStatisticsAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            final CombinedStatistics stats = getItem(position);
            PhraseGameStatistics phraseGameStats = stats.getPhraseGameStatistics();

            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(
                        R.layout.content_statistics_list_item, parent, false);
            }

            Button buttonClear = view.findViewById(R.id.btn_statistics_list_clear);
            buttonClear.setTag(position);
            buttonClear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    CombinedStatistics stats = getItem(position);
                    Log.d("stats", position + "");
                    new deleteKeyboardAsyncTask().execute(stats);
                }
            });

            TextView textViewKeyboardName = view.findViewById(
                    R.id.text_statistics_list_keyboard_name);
            textViewKeyboardName.setText(stats.getKeyboardData().name);

            TextView textViewPhraseSpeed = view.findViewById(
                    R.id.text_statistics_list_phrase_speed);
            textViewPhraseSpeed.setText(getString(R.string.text_statistics_phrase_speed,
                                                  phraseGameStats.getWordsPerMinute()));

            TextView textViewPhraseCorrect = view.findViewById(
                    R.id.text_statistics_list_phrase_correct);
            textViewPhraseCorrect.setText(getString(R.string.text_statistics_phrase_correct,
                                                    calculatePercent(
                                                            phraseGameStats.correctWordsCount,
                                                            phraseGameStats.wordsCount)));

            TextView textViewPhraseCorrectDiacritic = view.findViewById(
                    R.id.text_statistics_list_phrase_correct_diacritic);
            textViewPhraseCorrectDiacritic.setText(
                    getString(R.string.text_statistics_phrase_correct_diacritic,
                              calculatePercent(phraseGameStats.correctWordsDiacriticCount,
                                               phraseGameStats.wordsDiacriticCount)));


            TextView textViewCharSpeed = view.findViewById(
                    R.id.text_statistics_list_char_speed);
            textViewCharSpeed.setText(
                    getString(R.string.text_statistics_char_speed,
                              stats.getCharGameTotalStatistics().getCharsPerMinute()));

            displayCharStats(stats, CharType.ALPHA_LOWER, view,
                             R.id.text_statistics_list_char_lower_letters,
                             R.string.text_statistics_char_lower_letters);

            displayCharStats(stats, CharType.ALPHA_UPPER, view,
                             R.id.text_statistics_list_char_upper_letters,
                             R.string.text_statistics_char_upper_letters);

            displayCharStats(stats, CharType.DIGIT, view,
                             R.id.text_statistics_list_char_digits,
                             R.string.text_statistics_char_digits);

            displayCharStats(stats, CharType.SPECIAL, view,
                             R.id.text_statistics_list_char_special,
                             R.string.text_statistics_char_special);

            return view;
        }

        private float calculatePercent(float value, float total) {
            if (total == 0) {
                return 0;
            }

            return value / total * 100;
        }

        private void displayCharStats(CombinedStatistics stats, CharType charType, View view, int viewId, int stringId) {
            TextView textView = view.findViewById(viewId);
            for (CharGameStatistics stat : stats.getCharGameStatisticsList()) {
                if (stat.charType == charType) {
                    textView.setText(getString(stringId, calculatePercent(stat.correctCharsCount,
                                                                          stat.charsCount),
                                               stat.getCharsPerMinute()));
                    return;
                }
            }
            textView.setVisibility(View.GONE);
        }


    }
}
