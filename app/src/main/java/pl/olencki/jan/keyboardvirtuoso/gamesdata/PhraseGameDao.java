package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PhraseGameDao {
    @Query("SELECT * FROM PhraseGameData")
    List<PhraseGameData> findAll();

    @Query("SELECT * FROM PhraseGameData WHERE id = :phraseGameId")
    PhraseGameData findById(long phraseGameId);

    @Query("SELECT * FROM PhraseGameData WHERE keyboardId = :keyboardId")
    List<PhraseGameData> findByKeyboardId(long keyboardId);

    @Insert
    long insert(PhraseGameData phraseGame);

    @Insert
    long[] insertMultiple(List<PhraseGameData> phraseGames);

    @Delete
    int delete(PhraseGameData phraseGame);

    @Delete
    int deleteMultiple(List<PhraseGameData> phraseGame);

    @Query("DELETE FROM PhraseGameData WHERE keyboardId = :keyboardId")
    int deleteByKeyboardId(long keyboardId);
}
