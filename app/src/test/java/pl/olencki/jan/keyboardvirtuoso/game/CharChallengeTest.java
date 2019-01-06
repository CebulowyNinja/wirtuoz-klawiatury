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
        CharChallenge challenge = new CharChallenge(new CharWithType('a'));
        assertEquals(new CharWithType('a'), challenge.getCharWithType());

        challenge.setElapsedTime(1);
        assertEquals(1, challenge.getElapsedTime(), 0.001);

        challenge.setTypedCharWithType(new CharWithType('b'));
        assertEquals(new CharWithType('b'), challenge.getTypedCharWithType());
        assertFalse(challenge.isCorrect());
    }

    @Test
    public void testGenerateCharChallengeData() {
        CharChallenge challenge = new CharChallenge(new CharWithType('~'));

        try {
            challenge.generateCharChallengeData(null, 0);
            fail("Not throw when gameId = 0");
        } catch (CharChallengeException e) {
        }

        try {
            challenge.generateCharChallengeData(null, 1);
            fail("Not throw on unknown charType");
        } catch (CharChallengeException e) {
        }

        challenge = new CharChallenge(new CharWithType('a'));
        try {
            challenge.generateCharChallengeData(null, 1);
            fail("Not throw on typedCharWithType = null");
        } catch (CharChallengeException e) {
        }

        challenge.setTypedCharWithType(new CharWithType('a'));

        CharChallengeData data = new CharChallengeData(null, 1, CharType.ALPHA_LOWER,
                                                       'a', 'a', true, 0);
        try {
            assertEquals(data, challenge.generateCharChallengeData(null, 1));
        } catch (CharChallengeException e) {
            fail("Exception on correct data");
        }
    }
}