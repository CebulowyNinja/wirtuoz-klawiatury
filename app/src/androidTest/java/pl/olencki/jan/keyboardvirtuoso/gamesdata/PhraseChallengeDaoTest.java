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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PhraseChallengeDaoTest {
    private GamesDatabase database;
    private PhraseChallengeDao phraseChallengeDao;

    private KeyboardDao keyboardDao;
    private List<KeyboardData> keyboards = new ArrayList<>();

    private PhraseGameDao phraseGameDao;
    private List<PhraseGameData> games = new ArrayList<>();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, GamesDatabase.class).build();

        phraseChallengeDao = database.phraseChallengeDao();

        keyboardDao = database.keyboardDao();
        keyboards.add(new KeyboardData(null, "KeyboardData Test", "com.keyboard.test"));
        keyboardDao.insertMultiple(keyboards);
        keyboards = keyboardDao.findAll();

        phraseGameDao = database.phraseGameDao();
        games.add(new PhraseGameData(null, keyboards.get(0).id, new Date()));
        games.add(new PhraseGameData(null, keyboards.get(0).id, new Date()));
        phraseGameDao.insertMultiple(games);
        games = phraseGameDao.findAll();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void testReadInsertDelete() {
        PhraseChallengeData phraseChallengeSample = new PhraseChallengeData(null, games.get(0).id,
                                                                            "Test phrase", 1, 1, 1,
                                                                            "Test phrase", 1 , 1, 1, 1);
        phraseChallengeDao.insert(phraseChallengeSample);

        List<PhraseChallengeData> phraseChallenges = phraseChallengeDao.findAll();
        assertFalse(phraseChallenges.isEmpty());
        assertTrue(phraseChallenges.get(0).id > 0);

        PhraseChallengeData phraseChallengeById = phraseChallengeDao.findById(
                phraseChallenges.get(0).id);
        assertEquals(phraseChallenges.get(0), phraseChallengeById);

        List<PhraseChallengeData> phraseChallengesByGameId = phraseChallengeDao.findByGameId(
                keyboards.get(0).id);
        assertEquals(phraseChallenges.get(0), phraseChallengesByGameId.get(0));

        phraseChallengeDao.delete(phraseChallenges.get(0));
        assertTrue(phraseChallengeDao.findAll().isEmpty());
    }

    @Test
    public void testMultipleInsertDelete() {
        List<PhraseChallengeData> phraseChallengesSample = new ArrayList<>();
        phraseChallengesSample.add(
                new PhraseChallengeData(null, games.get(0).id, "Test phrase", 1, 1, 1,
                                        "Test phrase", 1 , 1, 1, 1));
        phraseChallengesSample.add(
                new PhraseChallengeData(null, games.get(0).id, "Test phrase", 1, 1, 1,
                                        "Test phrase", 1 , 1, 1, 1));
        phraseChallengeDao.insertMultiple(phraseChallengesSample);

        List<PhraseChallengeData> phraseChallenges = phraseChallengeDao.findAll();
        assertEquals(phraseChallengesSample.size(), phraseChallenges.size());

        phraseChallengeDao.deleteMultiple(phraseChallenges);
        assertTrue(phraseChallengeDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteByGameId() {
        List<PhraseChallengeData> phraseChallengesSample = new ArrayList<>();
        phraseChallengesSample.add(
                new PhraseChallengeData(null, games.get(0).id, "Test phrase", 1, 1, 1,
                                        "Test phrase", 1 , 1, 1, 1));
        phraseChallengesSample.add(
                new PhraseChallengeData(null, games.get(0).id, "Test phrase", 1, 1, 1,
                                        "Test phrase", 1 , 1, 1, 1));
        phraseChallengeDao.insertMultiple(phraseChallengesSample);

        phraseChallengeDao.deleteByGameId(games.get(0).id);
        List<PhraseChallengeData> charChallanges = phraseChallengeDao.findByGameId(games.get(0).id);
        assertTrue(charChallanges.isEmpty());

        phraseGameDao.delete(games.get(1));
        charChallanges = phraseChallengeDao.findByGameId(games.get(1).id);
        assertTrue(charChallanges.isEmpty());
    }
}