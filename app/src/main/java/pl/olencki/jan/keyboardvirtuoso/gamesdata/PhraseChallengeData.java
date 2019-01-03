package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(entity = PhraseGameData.class,
                    parentColumns = "id",
                    childColumns = "gameId",
                    onDelete = CASCADE,
                    onUpdate = CASCADE)
}, indices = {
        @Index("gameId")
})
public class PhraseChallengeData {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public long gameId;

    @NonNull
    public String phrase;
    public int phraseLength;
    public int wordsCount;
    public int wordsDiacriticCount;

    @NonNull
    public String typedPhrase;
    public int typedPhraseLength;
    public int correctWordsCount;
    public int correctWordsDiacriticCount;

    public int ellapsedTime;

    @Ignore
    public PhraseChallengeData(Long id, long gameId, @NonNull String phrase,
                               int phraseLength, int wordsCount, int wordsDiacriticCount) {
        this.id = id;
        this.gameId = gameId;
        this.phrase = phrase;
        this.phraseLength = phraseLength;
        this.wordsCount = wordsCount;
        this.wordsDiacriticCount = wordsDiacriticCount;
        this.typedPhrase = "";
    }

    public PhraseChallengeData(Long id, long gameId, @NonNull String phrase,
                               int phraseLength, int wordsCount, int wordsDiacriticCount,
                               @NonNull String typedPhrase, int typedPhraseLength,
                               int correctWordsCount, int correctWordsDiacriticCount,
                               int ellapsedTime) {
        this.id = id;
        this.gameId = gameId;
        this.phrase = phrase;
        this.phraseLength = phraseLength;
        this.wordsCount = wordsCount;
        this.wordsDiacriticCount = wordsDiacriticCount;
        this.typedPhrase = typedPhrase;
        this.typedPhraseLength = typedPhraseLength;
        this.correctWordsCount = correctWordsCount;
        this.correctWordsDiacriticCount = correctWordsDiacriticCount;
        this.ellapsedTime = ellapsedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhraseChallengeData that = (PhraseChallengeData) o;
        return gameId == that.gameId &&
                phraseLength == that.phraseLength &&
                wordsCount == that.wordsCount &&
                wordsDiacriticCount == that.wordsDiacriticCount &&
                typedPhraseLength == that.typedPhraseLength &&
                correctWordsCount == that.correctWordsCount &&
                correctWordsDiacriticCount == that.correctWordsDiacriticCount &&
                ellapsedTime == that.ellapsedTime &&
                Objects.equals(id, that.id) &&
                Objects.equals(phrase, that.phrase) &&
                Objects.equals(typedPhrase, that.typedPhrase);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gameId, phrase, phraseLength, wordsCount, wordsDiacriticCount,
                typedPhrase, typedPhraseLength, correctWordsCount,
                correctWordsDiacriticCount, ellapsedTime);
    }
}
