package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface KeyboardDao {
    @Query("SELECT * FROM keyboard")
    List<KeyboardData> findAll();

    @Query("SELECT * FROM keyboard WHERE id = :keyboardId")
    KeyboardData findById(long keyboardId);

    @Query("SELECT * FROM keyboard WHERE className = :className")
    KeyboardData getByClassName(String className);

    @Insert
    long insert(KeyboardData keyboard);

    @Insert
    long[] insertMultiple(List<KeyboardData> keyboards);

    @Delete
    int delete(KeyboardData keyboard);

    @Delete
    int deleteMultiple(List<KeyboardData> keyboards);

}
