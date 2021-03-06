package pl.olencki.jan.keyboardvirtuoso.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;

@Dao
public interface PhraseGameStatisticsDao {
    @Query("SELECT COUNT(Challenge.id) as phrasesCount, SUM(Challenge.isCorrect) as correctPhrasesCount, " +
            "SUM(Challenge.phraseLength) as totalLength, SUM(Challenge.typedPhraseLength) as totalTypedLength, " +
            "SUM(Challenge.elapsedTime) as elapsedTime," +
            "SUM(Challenge.wordsCount) as wordsCount, " +
            "SUM(Challenge.wordsDiacriticCount) as wordsDiacriticCount, " +
            "SUM(Challenge.correctWordsCount) as correctWordsCount, " +
            "SUM(Challenge.correctWordsDiacriticCount) as correctWordsDiacriticCount " +
            "FROM PhraseChallenge as Challenge")
    PhraseGameStatistics loadAll();

    @Query("SELECT COUNT(Challenge.id) as phrasesCount, SUM(Challenge.isCorrect) as correctPhrasesCount, " +
            "SUM(Challenge.phraseLength) as totalLength, SUM(Challenge.typedPhraseLength) as totalTypedLength, " +
            "SUM(Challenge.elapsedTime) as elapsedTime," +
            "SUM(Challenge.wordsCount) as wordsCount, " +
            "SUM(Challenge.wordsDiacriticCount) as wordsDiacriticCount, " +
            "SUM(Challenge.correctWordsCount) as correctWordsCount, " +
            "SUM(Challenge.correctWordsDiacriticCount) as correctWordsDiacriticCount " +
            "FROM PhraseChallenge as Challenge " +
            "INNER JOIN PhraseGame as Game ON Game.id = Challenge.gameId " +
            "WHERE Game.id = :gameId")
    PhraseGameStatistics loadByGameId(long gameId);


    @Query("SELECT COUNT(Challenge.id) as phrasesCount, SUM(Challenge.isCorrect) as correctPhrasesCount, " +
            "SUM(Challenge.phraseLength) as totalLength, SUM(Challenge.typedPhraseLength) as totalTypedLength, " +
            "SUM(Challenge.elapsedTime) as elapsedTime," +
            "SUM(Challenge.wordsCount) as wordsCount, " +
            "SUM(Challenge.wordsDiacriticCount) as wordsDiacriticCount, " +
            "SUM(Challenge.correctWordsCount) as correctWordsCount, " +
            "SUM(Challenge.correctWordsDiacriticCount) as correctWordsDiacriticCount " +
            "FROM PhraseChallenge as Challenge " +
            "INNER JOIN PhraseGame as Game ON Game.id = Challenge.gameId " +
            "INNER JOIN Keyboard ON Keyboard.id = Game.keyboardId " +
            "WHERE Keyboard.id = :keyboardId")
    PhraseGameStatistics loadByKeyboardId(long keyboardId);
}
