package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CharGameDao {
    @Query("SELECT * FROM CharGameData")
    List<CharGameData> findAll();

    @Query("SELECT * FROM CharGameData WHERE id = :charGameId")
    CharGameData findById(long charGameId);

    @Query("SELECT * FROM CharGameData WHERE keyboardId = :keyboardId")
    List<CharGameData> findByKeyboardId(long keyboardId);

    @Insert
    long insert(CharGameData charGame);

    @Insert
    long[] insertMultiple(List<CharGameData> charGames);

    @Delete
    int delete(CharGameData charGame);

    @Delete
    int deleteMultiple(List<CharGameData> charGame);

    @Query("DELETE FROM CharGameData WHERE keyboardId = :keyboardId")
    int deleteByKeyboardId(long keyboardId);
}
