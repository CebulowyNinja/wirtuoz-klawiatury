package pl.olencki.jan.keyboardvirtuoso.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

import java.util.List;

@Dao
public interface PhraseChallengeDao {
    @Query("SELECT * FROM PhraseChallenge")
    List<PhraseChallengeData> findAll();

    @Query("SELECT * FROM PhraseChallenge WHERE id = :phraseChallengeId")
    PhraseChallengeData findById(long phraseChallengeId);

    @Query("SELECT * FROM PhraseChallenge WHERE gameId = :gameId")
    List<PhraseChallengeData> findByGameId(long gameId);

    @Insert
    long insert(PhraseChallengeData phraseChallenge);

    @Insert
    long[] insertMultiple(List<PhraseChallengeData> phraseChallenges);

    @Delete
    int delete(PhraseChallengeData phraseChallenge);

    @Delete
    int deleteMultiple(List<PhraseChallengeData> phraseChallenges);

    @Query("DELETE FROM PhraseChallenge WHERE gameId = :gameId")
    int deleteByGameId(long gameId);
}
