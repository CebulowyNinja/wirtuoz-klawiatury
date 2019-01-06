package pl.olencki.jan.keyboardvirtuoso.game;

import org.junit.Test;

import pl.olencki.jan.keyboardvirtuoso.game.exception.PhraseChallengeException;
import pl.olencki.jan.keyboardvirtuoso.database.entities.PhraseChallengeData;

import static org.junit.Assert.*;

public class PhraseChallengeTest {
    @Test
    public void testGetters() {
        PhraseChallenge challenge = new PhraseChallenge(new Phrase("Test phrase one test"));
        assertEquals(new Phrase("Test phrase one test"), challenge.getPhrase());

        challenge.setElapsedTime(1);
        assertEquals(1, challenge.getElapsedTime(), 0.001);

        challenge.setTypedPhrase(new Phrase("Test phrase two"));
        assertEquals(new Phrase("Test phrase two"), challenge.getTypedPhrase());
        assertFalse(challenge.isCorrect());
        assertArrayEquals(new boolean[] {true, true, false, false}, challenge.areWordsCorrect());

        challenge.setTypedPhrase(new Phrase("Testphraseonetest"));
        assertFalse(challenge.isCorrect());
        assertArrayEquals(new boolean[] {false, false, false, true}, challenge.areWordsCorrect());

        challenge.setTypedPhrase(new Phrase(" Test   phrase    one   test  "));
        assertFalse(challenge.isCorrect());
        assertArrayEquals(new boolean[] {true, true, true, true}, challenge.areWordsCorrect());

        challenge.setTypedPhrase(new Phrase(" Test phhhhh one  test"));
        assertArrayEquals(new boolean[] {true, false, true, true}, challenge.areWordsCorrect());

        challenge.setTypedPhrase(new Phrase(" Test phhhhh noe test"));
        assertArrayEquals(new boolean[] {true, false, false, true}, challenge.areWordsCorrect());

        challenge.setTypedPhrase(new Phrase(" Test phrasedwa one test"));
        assertArrayEquals(new boolean[] {true, false, true, true}, challenge.areWordsCorrect());

        challenge.setTypedPhrase(new Phrase(" Test phrasedwaone test"));
        assertArrayEquals(new boolean[] {true, false, true, true}, challenge.areWordsCorrect());
    }

    @Test
    public void testGeneratePhraseChallengeData() {
        PhraseChallenge challenge = new PhraseChallenge(new Phrase("Test1"));

        try {
            challenge.generatePhraseChallengeData(null, 0);
            fail("Not throw when gameId = 0");
        } catch (PhraseChallengeException e) {
        }

        try {
            challenge.generatePhraseChallengeData(null, 1);
            fail("Not throw on unknown typedPhrase");
        } catch (PhraseChallengeException e) {
        }

        challenge.setTypedPhrase(new Phrase("Test2"));
        PhraseChallengeData data = new PhraseChallengeData(null, 1, "Test1", 5, 1, 0);
        data.isCorrect = false;
        data.typedPhrase = "Test2";
        data.typedPhraseLength = 5;
        data.correctWordsCount = 0;
        data.correctWordsDiacriticCount = 0;
        data.elapsedTime = 0;

        try {
            assertEquals(data, challenge.generatePhraseChallengeData(null, 1));
        } catch (PhraseChallengeException e) {
            fail("Exception on correct data");
        }
    }
}