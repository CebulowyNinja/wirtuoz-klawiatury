package pl.olencki.jan.keyboardvirtuoso.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "keyboard", indices = {
        @Index(value = {"className"}, unique = true)
})
public class KeyboardData {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @NonNull
    public String name;

    @NonNull
    public String className;

    public KeyboardData(Long id, @NonNull String name, @NonNull String className) {
        this.id = id;
        this.name = name;
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyboardData keyboard = (KeyboardData) o;
        return Objects.equals(id, keyboard.id) &&
                Objects.equals(name, keyboard.name) &&
                Objects.equals(className, keyboard.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, className);
    }
}
