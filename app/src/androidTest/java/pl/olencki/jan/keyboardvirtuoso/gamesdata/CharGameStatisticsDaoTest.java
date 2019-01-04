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

import pl.olencki.jan.keyboardvirtuoso.game.CharType;

import static org.junit.Assert.assertTrue;

public class CharGameStatisticsDaoTest {
    private GamesDatabase database;
    private CharGameStatisticsDao charGameStatisticsDao;

    private CharChallengeDao charChallengeDao;

    private KeyboardDao keyboardDao;
    private List<KeyboardData> keyboards = new ArrayList<>();

    private CharGameDao charGameDao;
    private List<CharGameData> games = new ArrayList<>();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, GamesDatabase.class).build();

        charGameStatisticsDao = database.charGameStatisticsDao();

        charChallengeDao = database.charChallengeDao();

        keyboardDao = database.keyboardDao();
        keyboards.add(new KeyboardData(null, "Keyboard Test", "com.keyboard.test"));
        keyboards.add(new KeyboardData(null, "Keyboard Test2", "com.keyboard.test2"));
        keyboardDao.insertMultiple(keyboards);
        keyboards = keyboardDao.findAll();

        charGameDao = database.charGameDao();
        games.add(new CharGameData(null, keyboards.get(0).id, new Date()));
        games.add(new CharGameData(null, keyboards.get(0).id, new Date()));
        games.add(new CharGameData(null, keyboards.get(1).id, new Date()));
        charGameDao.insertMultiple(games);
        games = charGameDao.findAll();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void testCharGameStatistics() {
        List<CharChallengeData> challenges = new ArrayList<>();

        challenges.add(generateChallenge(games.get(0).id, CharType.ALPHA_LOWER, true, 2));
        challenges.add(generateChallenge(games.get(0).id, CharType.ALPHA_LOWER, false, 2));
        challenges.add(generateChallenge(games.get(0).id, CharType.ALPHA_LOWER, true, 2));
        challenges.add(generateChallenge(games.get(0).id, CharType.ALPHA_UPPER, false, 2));

        challenges.add(generateChallenge(games.get(1).id, CharType.ALPHA_UPPER, true, 2));
        challenges.add(generateChallenge(games.get(1).id, CharType.ALPHA_UPPER, false, 2));

        challenges.add(generateChallenge(games.get(2).id, CharType.ALPHA_LOWER, true, 2));
        challenges.add(generateChallenge(games.get(2).id, CharType.ALPHA_LOWER, false, 2));

        charChallengeDao.insertMultiple(challenges);

        List<CharGameStatistics> statisticsAll = charGameStatisticsDao.loadAll();
        assertTrue(
                statisticsAll.contains(new CharGameStatistics(CharType.ALPHA_LOWER, 5, 3, 10, 6)));
        assertTrue(
                statisticsAll.contains(new CharGameStatistics(CharType.ALPHA_UPPER, 3, 1, 6, 2)));

        List<CharGameStatistics> statisticsByGame = charGameStatisticsDao.loadByGameId(
                games.get(0).id);
        assertTrue(statisticsByGame.contains(
                new CharGameStatistics(CharType.ALPHA_LOWER, 3, 2, 6, 4)));
        assertTrue(statisticsByGame.contains(
                new CharGameStatistics(CharType.ALPHA_UPPER, 1, 0, 2, 0)));


        List<CharGameStatistics> statisticsByKeyboard = charGameStatisticsDao.loadByKeyboardId(
                keyboards.get(0).id);
        assertTrue(statisticsByKeyboard.contains(
                new CharGameStatistics(CharType.ALPHA_LOWER, 3, 2, 6, 4)));
        assertTrue(statisticsByKeyboard.contains(
                new CharGameStatistics(CharType.ALPHA_UPPER, 3, 1, 6, 2)));
    }

    private CharChallengeData generateChallenge(long gameId, CharType charType, boolean isCorrect, int elapsedTime) {
        char[] correctChars = charType.getAllChars();
        CharChallengeData challenge = new CharChallengeData(null, gameId, charType, 'a');

        challenge.isCorrect = isCorrect;
        challenge.elapsedTime = elapsedTime;

        challenge.typedCharacter = 'a';

        return  challenge;
    }
}