package pl.olencki.jan.keyboardvirtuoso.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharWithTypeTest {
    @Test
    public void testGettersAndEquals() {
        CharWithType char1 = new CharWithType('a');
        CharWithType char2 = new CharWithType('a');
        CharWithType char3 = new CharWithType('A');
        CharWithType char4 = new CharWithType('~');

        assertEquals('a', char1.getChar());
        assertEquals(CharType.ALPHA_LOWER, char1.getCharType());
        assertNull(char4.getCharType());

        assertTrue(char1.equals(char1));
        assertTrue(char1.equals(char2));
        assertTrue(char2.equals(char1));

        assertFalse(char1.equals(char3));
        assertFalse(char3.equals(char1));
    }
}
