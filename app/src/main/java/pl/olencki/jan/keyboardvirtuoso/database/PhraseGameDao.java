package pl.olencki.jan.keyboardvirtuoso.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

import java.util.List;

@Dao
public interface PhraseGameDao {
    @Query("SELECT * FROM PhraseGame")
    List<PhraseGameData> findAll();

    @Query("SELECT * FROM PhraseGame WHERE id = :phraseGameId")
    PhraseGameData findById(long phraseGameId);

    @Query("SELECT * FROM PhraseGame WHERE keyboardId = :keyboardId")
    List<PhraseGameData> findByKeyboardId(long keyboardId);

    @Insert
    long insert(PhraseGameData phraseGame);

    @Insert
    long[] insertMultiple(List<PhraseGameData> phraseGames);

    @Delete
    int delete(PhraseGameData phraseGame);

    @Delete
    int deleteMultiple(List<PhraseGameData> phraseGame);

    @Query("DELETE FROM PhraseGame WHERE keyboardId = :keyboardId")
    int deleteByKeyboardId(long keyboardId);
}
