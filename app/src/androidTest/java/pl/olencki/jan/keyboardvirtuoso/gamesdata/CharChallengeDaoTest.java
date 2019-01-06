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
import pl.olencki.jan.keyboardvirtuoso.gamesdata.entities.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CharChallengeDaoTest {
    private GamesDatabase database;
    private CharChallengeDao charChallengeDao;

    private KeyboardDao keyboardDao;
    private List<KeyboardData> keyboards = new ArrayList<>();

    private CharGameDao charGameDao;
    private List<CharGameData> games = new ArrayList<>();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, GamesDatabase.class).build();

        charChallengeDao = database.charChallengeDao();

        keyboardDao = database.keyboardDao();
        keyboards.add(new KeyboardData(null, "Keyboard Test", "com.keyboard.test"));
        keyboardDao.insertMultiple(keyboards);
        keyboards = keyboardDao.findAll();

        charGameDao = database.charGameDao();
        games.add(new CharGameData(null, keyboards.get(0).id, new Date()));
        games.add(new CharGameData(null, keyboards.get(0).id, new Date()));
        charGameDao.insertMultiple(games);
        games = charGameDao.findAll();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void testReadInsertDelete() {
        CharChallengeData charChallengeSample = new CharChallengeData(null, games.get(0).id, CharType.ALPHA_LOWER, 'a');
        charChallengeDao.insert(charChallengeSample);

        List<CharChallengeData> charChallenges = charChallengeDao.findAll();
        assertFalse(charChallenges.isEmpty());
        assertTrue(charChallenges.get(0).id > 0);

        CharChallengeData charChallengeById = charChallengeDao.findById(charChallenges.get(0).id);
        assertEquals(charChallenges.get(0), charChallengeById);

        List<CharChallengeData> charChallengesByGameId = charChallengeDao.findByGameId(games.get(0).id);
        assertEquals(charChallenges.get(0), charChallengesByGameId.get(0));

        charChallengeDao.delete(charChallenges.get(0));
        assertTrue(charChallengeDao.findAll().isEmpty());
    }

    @Test
    public void testMultipleInsertDelete() {
        List<CharChallengeData> charChallengesSample = new ArrayList<>();
        charChallengesSample.add(new CharChallengeData(null, games.get(0).id, CharType.ALPHA_LOWER, 'a'));
        charChallengesSample.add(new CharChallengeData(null, games.get(0).id, CharType.ALPHA_LOWER, 'b'));
        charChallengeDao.insertMultiple(charChallengesSample);

        List<CharChallengeData> charChallenges = charChallengeDao.findAll();
        assertEquals(charChallengesSample.size(), charChallenges.size());

        charChallengeDao.deleteMultiple(charChallenges);
        assertTrue(charChallengeDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteByGameId() {
        List<CharChallengeData> charChallengesSample = new ArrayList<>();
        charChallengesSample.add(new CharChallengeData(null, games.get(0).id, CharType.ALPHA_LOWER, 'a'));
        charChallengesSample.add(new CharChallengeData(null, games.get(0).id, CharType.ALPHA_LOWER, 'b'));
        charChallengeDao.insertMultiple(charChallengesSample);

        charChallengeDao.deleteByGameId(games.get(0).id);
        List<CharChallengeData> charChallanges = charChallengeDao.findByGameId(games.get(0).id);
        assertTrue(charChallanges.isEmpty());

        charGameDao.delete(games.get(1));
        charChallanges = charChallengeDao.findByGameId(games.get(1).id);
        assertTrue(charChallanges.isEmpty());
    }

    @Test
    public void testCharTypeConvertion() {
        for (CharType charType : CharType.values()) {
            for(char character : charType.getAllChars()) {
                CharChallengeData charChallengeSample = new CharChallengeData(null, games.get(0).id, charType, character);
                long insertedId = charChallengeDao.insert(charChallengeSample);

                CharChallengeData charChallenge = charChallengeDao.findById(insertedId);
                assertEquals(charChallengeSample.charType, charChallenge.charType);
            }
        }
    }
}