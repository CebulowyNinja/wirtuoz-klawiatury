package pl.olencki.jan.keyboardvirtuoso.game;

import org.junit.Test;

import pl.olencki.jan.keyboardvirtuoso.game.exception.CharChallengeException;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharChallengeData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CharChallengeTest {
    @Test
    public void testGetters() {
        CharChallenge charChallenge = new CharChallenge(new CharWithType('a'));
        assertEquals(new CharWithType('a'), charChallenge.getCharWithType());

        charChallenge.setElapsedTime(1);
        assertEquals(1, charChallenge.getElapsedTime(), 0.001);

        charChallenge.setTypedCharWithType(new CharWithType('b'));
        assertEquals(new CharWithType('b'), charChallenge.getTypedCharWithType());
        assertFalse(charChallenge.isCorrect());

        charChallenge.setCharWithType(new CharWithType('b'));
        assertTrue(charChallenge.isCorrect());
    }

    @Test
    public void testGenerateCharChallengeData() {
        CharChallenge charChallenge = new CharChallenge(new CharWithType('~'));

        try {
            charChallenge.generateCharChallengeData(null, 0);
            fail("Not throw when gameId = 0");
        } catch (CharChallengeException e) {
        }

        try {
            charChallenge.generateCharChallengeData(null, 1);
            fail("Not throw on unknown charType");
        } catch (CharChallengeException e) {
        }

        charChallenge.setCharWithType(new CharWithType('a'));
        try {
            charChallenge.generateCharChallengeData(null, 1);
            fail("Not throw on typedCharWithType = null");
        } catch (CharChallengeException e) {
        }

        charChallenge.setTypedCharWithType(new CharWithType('~'));
        try {
            charChallenge.generateCharChallengeData(null, 1);
            fail("Not throw on unknown charType");
        } catch (CharChallengeException e) {
        }

        charChallenge.setTypedCharWithType(new CharWithType('a'));

        CharChallengeData data = new CharChallengeData(null, 1, CharType.ALPHA_LOWER,
                                                       'a', 'a', true, 0);
        try {
            assertEquals(data, charChallenge.generateCharChallengeData(null, 1));
        } catch (CharChallengeException e) {
            fail("Exception on correct data");
        }
    }
}