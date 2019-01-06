package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Ignore;

import java.util.Objects;

import pl.olencki.jan.keyboardvirtuoso.game.*;

public class CharGameStatistics {
    public CharType charType;

    public int charsCount;
    public int correctCharsCount;

    public float elapsedTime;
    public float elapsedTimeCorrect;

    @Ignore
    public CharGameStatistics(CharType charType) {
        this(charType, 0, 0, 0, 0);
    }

    public CharGameStatistics(CharType charType, int charsCount, int correctCharsCount, float elapsedTime, float elapsedTimeCorrect) {
        this.charType = charType;
        this.charsCount = charsCount;
        this.correctCharsCount = correctCharsCount;
        this.elapsedTime = elapsedTime;
        this.elapsedTimeCorrect = elapsedTimeCorrect;
    }

    @Ignore
    public float getCharsPerMinute() {
        if (elapsedTime == 0) {
            return 0;
        }
        return charsCount / elapsedTime * 60;
    }

    @Ignore
    public CharGameStatistics sumStatistics(CharGameStatistics stats) {
        CharGameStatistics totalStats = new CharGameStatistics(null);
        if (charType == stats.charType) {
            totalStats.charType = charType;
        }

        totalStats.charsCount = charsCount + stats.charsCount;
        totalStats.correctCharsCount = correctCharsCount + stats.correctCharsCount;
        totalStats.elapsedTime = elapsedTime + stats.elapsedTime;
        totalStats.elapsedTimeCorrect = elapsedTimeCorrect + stats.elapsedTimeCorrect;

        return totalStats;
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

    //TODO delete
    @Override
    public String toString() {
        return "CharGameStatistics{" +
                "charType=" + charType +
                ", charsCount=" + charsCount +
                ", correctCharsCount=" + correctCharsCount +
                ", elapsedTime=" + elapsedTime +
                ", elapsedTimeCorrect=" + elapsedTimeCorrect +
                '}';
    }
}
