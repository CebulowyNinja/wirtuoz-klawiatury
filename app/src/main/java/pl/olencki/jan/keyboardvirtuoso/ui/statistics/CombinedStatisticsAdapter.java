package pl.olencki.jan.keyboardvirtuoso.ui.statistics;


import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pl.olencki.jan.keyboardvirtuoso.*;
import pl.olencki.jan.keyboardvirtuoso.game.*;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.entities.*;

public class CombinedStatisticsAdapter extends ArrayAdapter<CombinedStatistics> {
    public CombinedStatisticsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @CallSuper
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        final CombinedStatistics stats = getItem(position);
        PhraseGameStatistics phraseGameStats = stats.getPhraseGameStatistics();

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.content_statistics_list_item, parent, false);
        }

        TextView textViewKeyboardName = view.findViewById(
                R.id.text_statistics_list_keyboard_name);
        textViewKeyboardName.setText(stats.getKeyboardData().name);

        TextView textViewPhraseSpeed = view.findViewById(
                R.id.text_statistics_list_phrase_speed);
        textViewPhraseSpeed.setText(getContext().getString(R.string.text_statistics_phrase_speed,
                phraseGameStats.getWordsPerMinute()));

        TextView textViewPhraseCorrect = view.findViewById(
                R.id.text_statistics_list_phrase_correct);
        textViewPhraseCorrect.setText(getContext().getString(R.string.text_statistics_phrase_correct,
                calculatePercent(
                        phraseGameStats.correctWordsCount,
                        phraseGameStats.wordsCount)));

        TextView textViewPhraseCorrectDiacritic = view.findViewById(
                R.id.text_statistics_list_phrase_correct_diacritic);
        textViewPhraseCorrectDiacritic.setText(
                getContext().getString(R.string.text_statistics_phrase_correct_diacritic,
                        calculatePercent(phraseGameStats.correctWordsDiacriticCount,
                                phraseGameStats.wordsDiacriticCount)));


        TextView textViewCharSpeed = view.findViewById(
                R.id.text_statistics_list_char_speed);
        textViewCharSpeed.setText(
                getContext().getString(R.string.text_statistics_char_speed,
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
                textView.setText(getContext().getString(stringId, calculatePercent(stat.correctCharsCount,
                        stat.charsCount),
                        stat.getCharsPerMinute()));
                return;
            }
        }
        textView.setVisibility(View.GONE);
    }


}

