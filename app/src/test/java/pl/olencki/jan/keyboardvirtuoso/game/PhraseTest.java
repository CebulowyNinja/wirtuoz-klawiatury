package pl.olencki.jan.keyboardvirtuoso.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhraseTest {

    @Test
    public void testBasicGetters() {
        String phrase = "Do you have   a bike";
        String[] words = {
                "Do",
                "you",
                "have",
                "a",
                "bike"
        };

        Phrase challengePhrase = new Phrase(phrase);

        assertEquals(challengePhrase.getText(), phrase);
        assertArrayEquals(challengePhrase.getWords(), words);
    }

    @Test
    public void testEquals() {
        Phrase phrase1 = new Phrase("Test");
        Phrase phrase2 = new Phrase("Test");
        Phrase phrase3 = new Phrase("Test22");

        assertTrue(phrase1.equals(phrase1));
        assertTrue(phrase1.equals(phrase2));
        assertTrue(phrase2.equals(phrase1));

        assertFalse(phrase1.equals(phrase3));
        assertFalse(phrase3.equals(phrase1));
    }

    @Test
    public void testDiacriticMethods() {
        String word = "łąka";
        assertEquals(Phrase.countDiacriticChars(word), 2);

        String phrase = "Coś ty narobił";
        String[] wordsWithDiacritics = {
                "Coś",
                "narobił"
        };

        Phrase challengePhrase = new Phrase(phrase);

        assertEquals(challengePhrase.getDiacriticCharsCount(), 2);
        assertArrayEquals(challengePhrase.getWordsWithDiacritics(), wordsWithDiacritics);
    }
}