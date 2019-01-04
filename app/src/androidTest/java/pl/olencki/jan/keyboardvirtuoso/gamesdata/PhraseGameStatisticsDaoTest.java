package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PhraseGameStatisticsDaoTest {
    private GamesDatabase database;
    private PhraseGameStatisticsDao phraseGameStatisticsDao;

    private PhraseChallengeDao phraseChallengeDao;

    private KeyboardDao keyboardDao;
    private List<KeyboardData> keyboards = new ArrayList<>();

    private PhraseGameDao phraseGameDao;
    private List<PhraseGameData> games = new ArrayList<>();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, GamesDatabase.class).build();

        phraseGameStatisticsDao = database.phraseGameStatisticsDao();

        phraseChallengeDao = database.phraseChallengeDao();

        keyboardDao = database.keyboardDao();
        keyboards.add(new KeyboardData(null, "KeyboardData Test", "com.keyboard.test"));
        keyboards.add(new KeyboardData(null, "KeyboardData Test2", "com.keyboard.test2"));
        keyboardDao.insertMultiple(keyboards);
        keyboards = keyboardDao.findAll();

        phraseGameDao = database.phraseGameDao();
        games.add(new PhraseGameData(null, keyboards.get(0).id, new Date()));
        games.add(new PhraseGameData(null, keyboards.get(0).id, new Date()));
        games.add(new PhraseGameData(null, keyboards.get(1).id, new Date()));
        phraseGameDao.insertMultiple(games);
        games = phraseGameDao.findAll();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void testPhraseGameStatistics() {
        List<PhraseChallengeData> challenges = new ArrayList<>();
        challenges.add(generateChallenge(games.get(0).id, 6, 4, 2, true, 2));
        challenges.add(generateChallenge(games.get(0).id, 6, 4, 2, false, 2));
        challenges.add(generateChallenge(games.get(0).id, 6, 4, 2, true, 2));
        challenges.add(generateChallenge(games.get(0).id, 6, 4, 2, false, 2));

        challenges.add(generateChallenge(games.get(1).id, 6, 4, 2, true, 2));
        challenges.add(generateChallenge(games.get(1).id, 6, 4, 2, false, 2));

        challenges.add(generateChallenge(games.get(2).id, 6, 4, 2, true, 2));
        challenges.add(generateChallenge(games.get(2).id, 6, 4, 2, false, 2));

        phraseChallengeDao.insertMultiple(challenges);

        PhraseGameStatistics statisticsAll = phraseGameStatisticsDao.loadAll();
        assertEquals(new PhraseGameStatistics(8, 4, 8*6, 4*6+4*5, 8*2, 8*4, 4*4+4*3, 4*2+4*1), statisticsAll);

        PhraseGameStatistics statisticsByGame = phraseGameStatisticsDao.loadByGameId(games.get(0).id);
        assertEquals(new PhraseGameStatistics(4, 2, 4*6, 2*6+2*5, 4*2, 4*4, 2*4+2*3, 2*2+2*1), statisticsByGame);

        PhraseGameStatistics statisticsByKeyboard = phraseGameStatisticsDao.loadByKeyboardId(keyboards.get(0).id);
        assertEquals(new PhraseGameStatistics(6, 3, 6*6, 3*6+3*5, 6*2, 6*4, 3*4+3*3, 3*2+3*1), statisticsByKeyboard);
    }

    private PhraseChallengeData generateChallenge(long gameId, int phraseLength, int wordsCount,
                                                  int wordsDiacriticCount, boolean isCorrect, int elapsedTime) {
        PhraseChallengeData challenge = new PhraseChallengeData(null, gameId, "", phraseLength,
                                                                wordsCount, wordsDiacriticCount);
        challenge.isCorrect = isCorrect;
        challenge.elapsedTime = elapsedTime;

        if(isCorrect) {
            challenge.typedPhraseLength = phraseLength;
            challenge.correctWordsCount = wordsCount;
            challenge.correctWordsDiacriticCount = wordsDiacriticCount;
        } else {
            challenge.typedPhraseLength = phraseLength - 1;
            challenge.correctWordsCount = wordsCount - 1;
            challenge.correctWordsDiacriticCount = wordsDiacriticCount - 1;
        }

        return challenge;
    }
}
