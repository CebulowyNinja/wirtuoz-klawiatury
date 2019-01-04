package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import java.util.Objects;

import pl.olencki.jan.keyboardvirtuoso.game.CharType;

public class CharGameStatistics {
    public CharType charType;

    public int charsCount;
    public int correctCharsCount;

    public int elapsedTime;
    public int elapsedTimeCorrect;

    public CharGameStatistics(CharType charType, int charsCount, int correctCharsCount, int elapsedTime, int elapsedTimeCorrect) {
        this.charType = charType;
        this.charsCount = charsCount;
        this.correctCharsCount = correctCharsCount;
        this.elapsedTime = elapsedTime;
        this.elapsedTimeCorrect = elapsedTimeCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharGameStatistics that = (CharGameStatistics) o;
        return charsCount == that.charsCount &&
                correctCharsCount == that.correctCharsCount &&
                elapsedTime == that.elapsedTime &&
                elapsedTimeCorrect == that.elapsedTimeCorrect &&
                charType == that.charType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(charType, charsCount, correctCharsCount, elapsedTime,
                            elapsedTimeCorrect);
    }
}
