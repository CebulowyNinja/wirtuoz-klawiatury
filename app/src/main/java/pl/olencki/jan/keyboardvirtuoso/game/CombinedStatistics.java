package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharGameStatistics;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.PhraseGameStatistics;

public class CombinedStatistics {
    private PhraseGameStatistics phraseGameStatistics;
    private List<CharGameStatistics> charGameStatisticsList;

    public CombinedStatistics(PhraseGameStatistics phraseGameStatistics, List<CharGameStatistics> charGameStatisticsList) {
        this.phraseGameStatistics = phraseGameStatistics;
        this.charGameStatisticsList = charGameStatisticsList;
    }

    public PhraseGameStatistics getPhraseGameStatistics() {
        return phraseGameStatistics;
    }

    public List<CharGameStatistics> getCharGameStatisticsList() {
        return charGameStatisticsList;
    }

    public CharGameStatistics getCharGameTotalStatistics() {
        CharGameStatistics totalStatistics = new CharGameStatistics(null);

        for(CharGameStatistics stats : charGameStatisticsList) {
            totalStatistics = totalStatistics.sumStatistics(stats);
        }

        return totalStatistics;
    }
}
