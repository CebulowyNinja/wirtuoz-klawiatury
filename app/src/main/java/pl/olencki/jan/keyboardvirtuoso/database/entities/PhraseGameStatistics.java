package pl.olencki.jan.keyboardvirtuoso.database.entities;

import android.arch.persistence.room.Ignore;

import java.util.Objects;

/**
 * Model class with generated or loaded statistics data of phrase game
 */
public class PhraseGameStatistics {
    public int phrasesCount;
    public int correctPhrasesCount;

    public int totalLength;
    public int totalTypedLength;

    public float elapsedTime;

    public int wordsCount;
    public int wordsDiacriticCount;
    public int correctWordsCount;
    public int correctWordsDiacriticCount;

    @Ignore
    public PhraseGameStatistics() {
    }

    public PhraseGameStatistics(int phrasesCount, int correctPhrasesCount, int totalLength, int totalTypedLength,
                                float elapsedTime, int wordsCount, int wordsDiacriticCount, int correctWordsCount, int correctWordsDiacriticCount) {
        this.phrasesCount = phrasesCount;
        this.correctPhrasesCount = correctPhrasesCount;
        this.totalLength = totalLength;
        this.totalTypedLength = totalTypedLength;
        this.elapsedTime = elapsedTime;
        this.wordsCount = wordsCount;
        this.wordsDiacriticCount = wordsDiacriticCount;
        this.correctWordsCount = correctWordsCount;
        this.correctWordsDiacriticCount = correctWordsDiacriticCount;
    }

    @Ignore
    public float getWordsPerMinute() {
        if (elapsedTime == 0) {
            return 0;
        }

        return (totalLength - phrasesCount) / elapsedTime * 60 / 5;
    }

    /**
     * Returns new statistics object with combined data from argument and this object
     *
     * @return new PhraseGameStatistics object
     */
    @Ignore
    public PhraseGameStatistics sumStatistics(PhraseGameStatistics stats) {
        PhraseGameStatistics totalStats = new PhraseGameStatistics();

        totalStats.phrasesCount = phrasesCount + stats.phrasesCount;
        totalStats.correctPhrasesCount = correctPhrasesCount + stats.correctPhrasesCount;
        totalStats.totalLength = totalLength + stats.totalLength;
        totalStats.totalTypedLength = totalTypedLength + stats.totalTypedLength;
        totalStats.elapsedTime = elapsedTime + stats.elapsedTime;
        totalStats.wordsCount = wordsCount + stats.wordsCount;
        totalStats.wordsDiacriticCount = wordsDiacriticCount + stats.wordsDiacriticCount;
        totalStats.correctWordsCount = correctWordsCount + stats.correctWordsCount;
        totalStats.correctWordsDiacriticCount = correctWordsDiacriticCount + stats.correctWordsDiacriticCount;

        return totalStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhraseGameStatistics that = (PhraseGameStatistics) o;
        return phrasesCount == that.phrasesCount &&
                correctPhrasesCount == that.correctPhrasesCount &&
                totalLength == that.totalLength &&
                totalTypedLength == that.totalTypedLength &&
                elapsedTime == that.elapsedTime &&
                wordsCount == that.wordsCount &&
                correctWordsCount == that.correctWordsCount &&
                correctWordsDiacriticCount == that.correctWordsDiacriticCount;
    }

    @Override
    public int hashCode() {

        return Objects.hash(phrasesCount, correctPhrasesCount, totalLength, totalTypedLength,
                elapsedTime, wordsCount, correctWordsCount, correctWordsDiacriticCount);
    }
}