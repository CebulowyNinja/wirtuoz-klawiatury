package pl.olencki.jan.keyboardvirtuoso.database.converters;

import android.arch.persistence.room.TypeConverter;
import pl.olencki.jan.keyboardvirtuoso.game.*;

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
