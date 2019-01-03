package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PhraseChallengeDao {
    @Query("SELECT * FROM PhraseChallengeData")
    List<PhraseChallengeData> findAll();

    @Query("SELECT * FROM PhraseChallengeData WHERE id = :phraseChallengeId")
    PhraseChallengeData findById(long phraseChallengeId);

    @Query("SELECT * FROM PhraseChallengeData WHERE gameId = :gameId")
    List<PhraseChallengeData> findByGameId(long gameId);

    @Insert
    long insert(PhraseChallengeData phraseChallenge);

    @Insert
    long[] insertMultiple(List<PhraseChallengeData> phraseChallenges);

    @Delete
    int delete(PhraseChallengeData phraseChallenge);

    @Delete
    int deleteMultiple(List<PhraseChallengeData> phraseChallenges);

    @Query("DELETE FROM PhraseChallengeData WHERE gameId = :gameId")
    int deleteByGameId(long gameId);
}