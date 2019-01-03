package pl.olencki.jan.keyboardvirtuoso.gamesdata;

import android.arch.persistence.room.TypeConverter;

import pl.olencki.jan.keyboardvirtuoso.game.CharType;

public class CharTypeConverters {
    @TypeConverter
    public static CharType fromString(String val) {
        return CharType.valueOf(val);
    }

    @TypeConverter
    public static String charTypeToString(CharType charType) {
        return charType == null ? null : charType.toString();
    }
}
