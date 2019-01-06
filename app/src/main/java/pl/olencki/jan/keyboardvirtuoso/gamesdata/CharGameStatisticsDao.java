package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.entities.*;

import java.util.List;

@Dao
public interface CharGameStatisticsDao {
    @Query("SELECT COUNT(Challenge.id) as charsCount, SUM(Challenge.isCorrect) as correctCharsCount, " +
            "Challenge.charType as charType, " +
            "SUM(Challenge.elapsedTime) as elapsedTime, SUM(Challenge.elapsedTime * Challenge.isCorrect) as elapsedTimeCorrect " +
            "FROM CharChallenge as Challenge GROUP BY Challenge.charType")
    List<CharGameStatistics> loadAll();

    @Query("SELECT COUNT(Challenge.id) as charsCount, SUM(Challenge.isCorrect) as correctCharsCount, " +
            "Challenge.charType as charType, " +
            "SUM(Challenge.elapsedTime) as elapsedTime, SUM(Challenge.elapsedTime * Challenge.isCorrect) as elapsedTimeCorrect " +
            "FROM CharChallenge as Challenge " +
            "INNER JOIN CharGame as Game ON Game.id = Challenge.gameId " +
            "WHERE Game.id = :gameId " +
            "GROUP BY Challenge.charType")
    List<CharGameStatistics> loadByGameId(long gameId);


    @Query("SELECT COUNT(Challenge.id) as charsCount, SUM(Challenge.isCorrect) as correctCharsCount, " +
            "Challenge.charType as charType, " +
            "SUM(Challenge.elapsedTime) as elapsedTime, SUM(Challenge.elapsedTime * Challenge.isCorrect) as elapsedTimeCorrect " +
            "FROM CharChallenge as Challenge " +
            "INNER JOIN CharGame as Game ON Game.id = Challenge.gameId " +
            "INNER JOIN Keyboard ON Keyboard.id = Game.keyboardId " +
            "WHERE Keyboard.id = :keyboardId " +
            "GROUP BY Challenge.charType")
    List<CharGameStatistics> loadByKeyboardId(long keyboardId);
}
