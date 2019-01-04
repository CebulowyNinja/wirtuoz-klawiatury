package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(version = 1,
          entities = {
                  KeyboardData.class, PhraseGameData.class, CharGameData.class, CharChallengeData.class, PhraseChallengeData.class
          }, exportSchema = false)
@TypeConverters({
                        DateConverters.class,
                        CharTypeConverters.class
                })
abstract class GamesDatabase extends RoomDatabase {
    abstract public KeyboardDao keyboardDao();

    abstract public PhraseGameDao phraseGameDao();

    abstract public CharGameDao charGameDao();

    abstract public CharChallengeDao charChallengeDao();

    abstract public PhraseChallengeDao phraseChallengeDao();

    abstract public CharGameStatisticsDao charGameStatisticsDao();

    abstract public PhraseGameStatisticsDao phraseGameStatisticsDao();
}
