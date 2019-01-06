package pl.olencki.jan.keyboardvirtuoso.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

import java.util.List;

@Dao
public interface CharChallengeDao {
    @Query("SELECT * FROM CharChallenge")
    List<CharChallengeData> findAll();

    @Query("SELECT * FROM CharChallenge WHERE id = :charChallengeId")
    CharChallengeData findById(long charChallengeId);

    @Query("SELECT * FROM CharChallenge WHERE gameId = :gameId")
    List<CharChallengeData> findByGameId(long gameId);

    @Insert
    long insert(CharChallengeData charChallenge);

    @Insert
    long[] insertMultiple(List<CharChallengeData> charChallenges);

    @Delete
    int delete(CharChallengeData charChallenge);

    @Delete
    int deleteMultiple(List<CharChallengeData> charChallenges);

    @Query("DELETE FROM CharChallenge WHERE gameId = :gameId")
    int deleteByGameId(long gameId);
}
