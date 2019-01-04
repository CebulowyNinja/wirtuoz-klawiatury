package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import java.util.Objects;

public class PhraseGameStatistics {
    public int phrasesCount;
    public int correctPhrasesCount;

    public int totalLength;
    public int totalTypedLength;
    public int elapsedTime;

    public int wordsCount;
    public int correctWordsCount;
    public int correctWordsDiacriticCount;

    public PhraseGameStatistics(int phrasesCount, int correctPhrasesCount, int totalLength, int totalTypedLength,
                                int elapsedTime, int wordsCount, int correctWordsCount, int correctWordsDiacriticCount) {
        this.phrasesCount = phrasesCount;
        this.correctPhrasesCount = correctPhrasesCount;
        this.totalLength = totalLength;
        this.totalTypedLength = totalTypedLength;
        this.elapsedTime = elapsedTime;
        this.wordsCount = wordsCount;
        this.correctWordsCount = correctWordsCount;
        this.correctWordsDiacriticCount = correctWordsDiacriticCount;
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