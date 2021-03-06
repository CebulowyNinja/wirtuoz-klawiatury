package pl.olencki.jan.keyboardvirtuoso.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "CharGame",
        foreignKeys = {
                @ForeignKey(entity = KeyboardData.class,
                        parentColumns = "id",
                        childColumns = "keyboardId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE)
        }, indices = {
        @Index("keyboardId")
})
public class CharGameData {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public long keyboardId;

    @NonNull
    public Date date;

    public CharGameData(Long id, long keyboardId, @NonNull Date date) {
        this.id = id;
        this.keyboardId = keyboardId;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharGameData that = (CharGameData) o;
        return keyboardId == that.keyboardId &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, keyboardId, date);
    }
}
