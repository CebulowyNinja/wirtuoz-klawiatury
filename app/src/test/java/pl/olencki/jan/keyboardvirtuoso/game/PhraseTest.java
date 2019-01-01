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
    public void testDiacriticMethods() {
        String word = "łąka";
        assertEquals(Phrase.countDiacriticChars(word), 2);

        String phrase = "Coś ty narobił";
        String[] words = {
                "Coś",
                "narobił"
        };
        Phrase challengePhrase = new Phrase(phrase);
        assertEquals(challengePhrase.getDiacriticCharsCount(), 2);
        assertArrayEquals(challengePhrase.getWordsWithDiacritic(), words);
    }
}