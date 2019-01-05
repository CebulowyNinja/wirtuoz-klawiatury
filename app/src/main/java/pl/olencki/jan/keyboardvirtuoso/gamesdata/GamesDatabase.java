package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(version = 1,
          entities = {
                  KeyboardData.class, PhraseGameData.class, CharGameData.class, CharChallengeData.class, PhraseChallengeData.class
          }, exportSchema = false)
@TypeConverters({
                        DateConverters.class,
                        CharTypeConverters.class
                })
public abstract class GamesDatabase extends RoomDatabase {
    private static final String DATABASE_FILE = "gameDatabase.db";
    private static volatile GamesDatabase instance;

    protected GamesDatabase() {
    }

    public static synchronized GamesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static GamesDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                GamesDatabase.class,
                DATABASE_FILE).build();
    }

    abstract public KeyboardDao keyboardDao();

    abstract public PhraseGameDao phraseGameDao();

    abstract public CharGameDao charGameDao();

    abstract public CharChallengeDao charChallengeDao();

    abstract public PhraseChallengeDao phraseChallengeDao();

    abstract public CharGameStatisticsDao charGameStatisticsDao();

    abstract public PhraseGameStatisticsDao phraseGameStatisticsDao();
}
