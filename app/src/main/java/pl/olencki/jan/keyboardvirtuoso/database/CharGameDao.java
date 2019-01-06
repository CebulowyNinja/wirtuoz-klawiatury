package pl.olencki.jan.keyboardvirtuoso.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

import java.util.List;

@Dao
public interface CharGameDao {
    @Query("SELECT * FROM CharGame")
    List<CharGameData> findAll();

    @Query("SELECT * FROM CharGame WHERE id = :charGameId")
    CharGameData findById(long charGameId);

    @Query("SELECT * FROM CharGame WHERE keyboardId = :keyboardId")
    List<CharGameData> findByKeyboardId(long keyboardId);

    @Insert
    long insert(CharGameData charGame);

    @Insert
    long[] insertMultiple(List<CharGameData> charGames);

    @Delete
    int delete(CharGameData charGame);

    @Delete
    int deleteMultiple(List<CharGameData> charGame);

    @Query("DELETE FROM CharGame WHERE keyboardId = :keyboardId")
    int deleteByKeyboardId(long keyboardId);
}
