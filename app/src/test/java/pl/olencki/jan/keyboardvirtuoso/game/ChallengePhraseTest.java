package pl.olencki.jan.keyboardvirtuoso.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChallengePhraseTest {

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
        ChallengePhrase challengePhrase = new ChallengePhrase(phrase);
        assertEquals(challengePhrase.getPhrase(), phrase);
        assertArrayEquals(challengePhrase.getWords(), words);
    }

    @Test
    public void testDiacriticMethods() {
        String word = "łąka";
        assertEquals(ChallengePhrase.countDiacriticChars(word), 2);

        String phrase = "Coś ty narobił";
        String[] words = {
                "Coś",
                "narobił"
        };
        ChallengePhrase challengePhrase = new ChallengePhrase(phrase);
        assertArrayEquals(challengePhrase.getWordsWithDiacritic(), words);
    }
}