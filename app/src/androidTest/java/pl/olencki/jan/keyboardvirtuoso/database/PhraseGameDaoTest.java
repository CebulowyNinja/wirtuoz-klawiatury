package pl.olencki.jan.keyboardvirtuoso.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PhraseGameDaoTest {
    private GamesDatabase database;
    private PhraseGameDao phraseGameDao;

    private KeyboardDao keyboardDao;
    private List<KeyboardData> keyboards = new ArrayList<>();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, GamesDatabase.class).build();

        phraseGameDao = database.phraseGameDao();

        keyboardDao = database.keyboardDao();
        keyboards.add(new KeyboardData(null, "Keyboard Test", "com.keyboard.test"));
        keyboards.add(new KeyboardData(null, "Keyboard Test 2", "com.keyboard.test2"));
        keyboardDao.insertMultiple(keyboards);
        keyboards = keyboardDao.findAll();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void testReadInsertDelete() {
        PhraseGameData phraseGameSample = new PhraseGameData(null, keyboards.get(0).id, new Date());
        phraseGameDao.insert(phraseGameSample);

        List<PhraseGameData> phraseGames = phraseGameDao.findAll();
        assertFalse(phraseGames.isEmpty());
        assertTrue(phraseGames.get(0).id > 0);

        PhraseGameData phraseGameById = phraseGameDao.findById(phraseGames.get(0).id);
        assertEquals(phraseGames.get(0), phraseGameById);

        List<PhraseGameData> phraseGamesByKeyboardId = phraseGameDao.findByKeyboardId(keyboards.get(0).id);
        assertEquals(phraseGames.get(0), phraseGamesByKeyboardId.get(0));

        phraseGameDao.delete(phraseGames.get(0));
        assertTrue(phraseGameDao.findAll().isEmpty());
    }

    @Test
    public void testMultipleInsertDelete() {
        List<PhraseGameData> phraseGamesSample = new ArrayList<>();
        phraseGamesSample.add(new PhraseGameData(null, keyboards.get(0).id, new Date()));
        phraseGamesSample.add(new PhraseGameData(null, keyboards.get(1).id, new Date()));
        phraseGameDao.insertMultiple(phraseGamesSample);

        List<PhraseGameData> phraseGames = phraseGameDao.findAll();
        assertEquals(phraseGamesSample.size(), phraseGames.size());

        phraseGameDao.deleteMultiple(phraseGames);
        assertTrue(phraseGameDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteByKeyboardId() {
        List<PhraseGameData> phraseGamesSample = new ArrayList<>();
        phraseGamesSample.add(new PhraseGameData(null, keyboards.get(0).id, new Date()));
        phraseGamesSample.add(new PhraseGameData(null, keyboards.get(1).id, new Date()));
        phraseGameDao.insertMultiple(phraseGamesSample);

        phraseGameDao.deleteByKeyboardId(keyboards.get(0).id);
        List<PhraseGameData> phraseGames = phraseGameDao.findByKeyboardId(keyboards.get(0).id);
        assertTrue(phraseGames.isEmpty());

        keyboardDao.delete(keyboards.get(1));
        phraseGames = phraseGameDao.findByKeyboardId(keyboards.get(1).id);
        assertTrue(phraseGames.isEmpty());
    }

    @Test
    public void testDateConvertion() {
        Date date = new Date();
        PhraseGameData phraseGameSample = new PhraseGameData(null, keyboards.get(0).id, date);
        phraseGameDao.insert(phraseGameSample);

        List<PhraseGameData> phraseGames = phraseGameDao.findAll();
        assertEquals(date, phraseGames.get(0).date);
    }
}