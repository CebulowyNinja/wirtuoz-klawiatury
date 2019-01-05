package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

import pl.olencki.jan.keyboardvirtuoso.game.CharType;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "CharChallenge",
        foreignKeys = {
                @ForeignKey(entity = CharGameData.class,
                            parentColumns = "id",
                            childColumns = "gameId",
                            onDelete = CASCADE,
                            onUpdate = CASCADE)
        }, indices = {
        @Index("gameId")
})
public class CharChallengeData {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public long gameId;

    @NonNull
    public CharType charType;
    public char character;

    public char typedCharacter;
    public boolean isCorrect;

    public float elapsedTime;

    @Ignore
    public CharChallengeData(Long id, long gameId, @NonNull CharType charType, char character) {
        this.id = id;
        this.gameId = gameId;
        this.charType = charType;
        this.character = character;
    }

    public CharChallengeData(Long id, long gameId, @NonNull CharType charType, char character,
                             char typedCharacter, boolean isCorrect, float elapsedTime) {
        this.id = id;
        this.gameId = gameId;
        this.charType = charType;
        this.character = character;
        this.typedCharacter = typedCharacter;
        this.isCorrect = isCorrect;
        this.elapsedTime = elapsedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharChallengeData that = (CharChallengeData) o;
        return gameId == that.gameId &&
                character == that.character &&
                typedCharacter == that.typedCharacter &&
                isCorrect == that.isCorrect &&
                Objects.equals(id, that.id) &&
                charType == that.charType &&
                Math.abs(elapsedTime - that.elapsedTime) < 0.001;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gameId, charType, character, typedCharacter, isCorrect,
                            elapsedTime);
    }
}
