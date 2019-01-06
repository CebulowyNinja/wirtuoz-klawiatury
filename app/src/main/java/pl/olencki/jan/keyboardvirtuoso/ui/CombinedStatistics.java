package pl.olencki.jan.keyboardvirtuoso.ui;

import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.gamesdata.*;

public class CombinedStatistics {
    private KeyboardData keyboardData;
    private PhraseGameStatistics phraseGameStatistics;
    private List<CharGameStatistics> charGameStatisticsList;

    public CombinedStatistics(KeyboardData keyboardData, PhraseGameStatistics phraseGameStatistics, List<CharGameStatistics> charGameStatisticsList) {
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
