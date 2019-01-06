package pl.olencki.jan.keyboardvirtuoso.ui.statistics;


import android.os.AsyncTask;
import pl.olencki.jan.keyboardvirtuoso.app.*;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.*;
import pl.olencki.jan.keyboardvirtuoso.ui.*;

abstract public class DeleteKeyboardAsyncTask extends AsyncTask<CombinedStatistics, Void, CombinedStatistics[]> {
    @Override
    protected CombinedStatistics[] doInBackground(CombinedStatistics... statsArray) {
        GamesDatabase db = GamesDatabase.getInstance(App.getAppContext());
        KeyboardDao keyboardDao = db.keyboardDao();

        for (CombinedStatistics stat : statsArray) {
            keyboardDao.delete(stat.getKeyboardData());
        }

        return statsArray;
    }
}
