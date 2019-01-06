package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.entities.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CharGameDaoTest {
    private GamesDatabase database;
    private CharGameDao charGameDao;

    private KeyboardDao keyboardDao;
    private List<KeyboardData> keyboards = new ArrayList<>();

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, GamesDatabase.class).build();

        charGameDao = database.charGameDao();

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
        CharGameData charGameSample = new CharGameData(null, keyboards.get(0).id, new Date());
        charGameDao.insert(charGameSample);

        List<CharGameData> charGames = charGameDao.findAll();
        assertFalse(charGames.isEmpty());
        assertTrue(charGames.get(0).id > 0);

        CharGameData charGameById = charGameDao.findById(charGames.get(0).id);
        assertEquals(charGames.get(0), charGameById);

        List<CharGameData> charGamesByKeyboardId = charGameDao.findByKeyboardId(keyboards.get(0).id);
        assertEquals(charGames.get(0), charGamesByKeyboardId.get(0));

        charGameDao.delete(charGames.get(0));
        assertTrue(charGameDao.findAll().isEmpty());
    }

    @Test
    public void testMultipleInsertDelete() {
        List<CharGameData> charGamesSample = new ArrayList<>();
        charGamesSample.add(new CharGameData(null, keyboards.get(0).id, new Date()));
        charGamesSample.add(new CharGameData(null, keyboards.get(1).id, new Date()));
        charGameDao.insertMultiple(charGamesSample);

        List<CharGameData> charGames = charGameDao.findAll();
        assertEquals(charGamesSample.size(), charGames.size());

        charGameDao.deleteMultiple(charGames);
        assertTrue(charGameDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteByKeyboardId() {
        List<CharGameData> charGamesSample = new ArrayList<>();
        charGamesSample.add(new CharGameData(null, keyboards.get(0).id, new Date()));
        charGamesSample.add(new CharGameData(null, keyboards.get(1).id, new Date()));
        charGameDao.insertMultiple(charGamesSample);

        charGameDao.deleteByKeyboardId(keyboards.get(0).id);
        List<CharGameData> charGames = charGameDao.findByKeyboardId(keyboards.get(0).id);
        assertTrue(charGames.isEmpty());

        keyboardDao.delete(keyboards.get(1));
        charGames = charGameDao.findByKeyboardId(keyboards.get(1).id);
        assertTrue(charGames.isEmpty());
    }

    @Test
    public void testDateConvertion() {
        Date date = new Date();
        CharGameData charGameSample = new CharGameData(null, keyboards.get(0).id, date);
        charGameDao.insert(charGameSample);

        List<CharGameData> charGames = charGameDao.findAll();
        assertEquals(date, charGames.get(0).date);
    }
}