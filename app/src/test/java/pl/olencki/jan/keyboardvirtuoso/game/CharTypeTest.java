package pl.olencki.jan.keyboardvirtuoso.game;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CharTypeTest {
    @Test
    public void testAllValues() {
        CharType[] values = CharType.values();

        for (CharType val : values) {
            char[] allChars = val.getAllChars();

            for(char character : allChars) {
                assertTrue(val.isContainsChar(character));
            }
        }
    }
}