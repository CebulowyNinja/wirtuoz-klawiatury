package pl.olencki.jan.keyboardvirtuoso.database;

import android.arch.persistence.room.*;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

import java.util.List;

@Dao
public interface KeyboardDao {
    @Query("SELECT * FROM keyboard")
    List<KeyboardData> findAll();

    @Query("SELECT * FROM keyboard WHERE id = :keyboardId")
    KeyboardData findById(long keyboardId);

    @Query("SELECT * FROM keyboard WHERE className = :className")
    KeyboardData findByClassName(String className);

    @Insert
    long insert(KeyboardData keyboard);

    @Insert
    long[] insertMultiple(List<KeyboardData> keyboards);

    @Update
    int update(KeyboardData keyboard);

    @Delete
    int delete(KeyboardData keyboard);

    @Delete
    int deleteMultiple(List<KeyboardData> keyboards);

}
