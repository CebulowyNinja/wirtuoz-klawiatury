package pl.olencki.jan.keyboardvirtuoso.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KeyboardDaoTest {
    private GamesDatabase database;
    private KeyboardDao keyboardDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, GamesDatabase.class).build();

        keyboardDao = database.keyboardDao();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void testReadInsertDelete() {
        KeyboardData keyboardSample = new KeyboardData(null, "Test keyboard", "com.keyboard.test");

        keyboardDao.insert(keyboardSample);

        List<KeyboardData> keyboards = keyboardDao.findAll();
        assertFalse(keyboards.isEmpty());
        assertTrue(keyboards.get(0).id > 0);

        KeyboardData keyboardById = keyboardDao.findById(keyboards.get(0).id);
        assertEquals(keyboards.get(0), keyboardById);

        KeyboardData keyboardByClassName = keyboardDao.findByClassName(keyboards.get(0).className);
        assertEquals(keyboards.get(0), keyboardByClassName);

        keyboardDao.delete(keyboards.get(0));
        assertTrue(keyboardDao.findAll().isEmpty());
    }

    @Test
    public void testMultipleInsertDelete() {
        List<KeyboardData> keyboardsSample = new ArrayList<>();
        keyboardsSample.add(new KeyboardData(null, "Test1", "com.keyboard.test1"));
        keyboardsSample.add(new KeyboardData(null, "Test2", "com.keyboard.test2"));

        keyboardDao.insertMultiple(keyboardsSample);
        List<KeyboardData> keyboards = keyboardDao.findAll();
        assertEquals(keyboardsSample.size(), keyboards.size());

        keyboardDao.deleteMultiple(keyboards);
        assertTrue(keyboardDao.findAll().isEmpty());
    }
}