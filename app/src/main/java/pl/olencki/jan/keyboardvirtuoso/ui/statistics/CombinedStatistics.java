package pl.olencki.jan.keyboardvirtuoso.ui.statistics;

import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

import java.util.List;

/**
 * Model class with all statistics data for keyboard used in statistics activity
 */
public class CombinedStatistics {
    private KeyboardData keyboardData;
    private PhraseGameStatistics phraseGameStatistics;
    private List<CharGameStatistics> charGameStatisticsList;

    public CombinedStatistics(KeyboardData keyboardData, PhraseGameStatistics phraseGameStatistics,
                              List<CharGameStatistics> charGameStatisticsList) {
        this.keyboardData = keyboardData;
        this.phraseGameStatistics = phraseGameStatistics;
        this.charGameStatisticsList = charGameStatisticsList;
    }

    public KeyboardData getKeyboardData() {
        return keyboardData;
    }

    public PhraseGameStatistics getPhraseGameStatistics() {
        return phraseGameStatistics;
    }

    public List<CharGameStatistics> getCharGameStatisticsList() {
        return charGameStatisticsList;
    }

    public CharGameStatistics getCharGameTotalStatistics() {
        CharGameStatistics totalStatistics = new CharGameStatistics(null);

        for (CharGameStatistics stats : charGameStatisticsList) {
            totalStatistics = totalStatistics.sumStatistics(stats);
        }

        return totalStatistics;
    }
}
