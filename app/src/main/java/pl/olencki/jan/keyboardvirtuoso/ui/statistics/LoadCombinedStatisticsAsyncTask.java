package pl.olencki.jan.keyboardvirtuoso.ui.statistics;

import android.os.AsyncTask;
import pl.olencki.jan.keyboardvirtuoso.app.*;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.*;
import pl.olencki.jan.keyboardvirtuoso.ui.*;

import java.util.ArrayList;
import java.util.List;

abstract public class LoadCombinedStatisticsAsyncTask extends AsyncTask<Void, Void, List<CombinedStatistics>> {
    @Override
    protected List<CombinedStatistics> doInBackground(Void... voids) {
        GamesDatabase db = GamesDatabase.getInstance(App.getAppContext());

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
}