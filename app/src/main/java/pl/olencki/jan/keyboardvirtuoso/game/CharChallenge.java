package pl.olencki.jan.keyboardvirtuoso.game;

import pl.olencki.jan.keyboardvirtuoso.game.exception.CharChallengeException;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharChallengeData;

public class CharChallenge extends Challenge {
    private CharWithType charWithType;
    private CharWithType typedCharWithType;

    public CharChallenge(CharWithType charWithType) {
        this.charWithType = charWithType;
    }

    public CharWithType getCharWithType() {
        return charWithType;
    }

    public CharWithType getTypedCharWithType() {
        return typedCharWithType;
    }

    public void setTypedCharWithType(CharWithType typedCharWithType) {
        this.typedCharWithType = typedCharWithType;
    }

    @Override
    public boolean isCorrect() {
        return charWithType.equals(typedCharWithType);
    }

    @Override
    public String getText() {
        return Character.toString(charWithType.getChar());
    }

    @Override
    public String getTypedText() {
        if (typedCharWithType == null) {
            return null;
        }

        return Character.toString(typedCharWithType.getChar());
    }

    public CharChallengeData generateCharChallengeData(Long id, long gameId) throws CharChallengeException {
        final String exceptionMsg = "Unable to create CharChallengeData object: ";
        CharChallengeData data;

        if (gameId <= 0) {
            throw new CharChallengeException(exceptionMsg + "gameId must be grather than 0");
        }

        if (charWithType.getCharType() == null) {
            throw new CharChallengeException(exceptionMsg + "unknown charType in charWithType");
        }

        if (typedCharWithType == null) {
            throw new CharChallengeException(exceptionMsg + "typedCharWithType can't be null");
        }

        data = new CharChallengeData(id, gameId,
                                     charWithType.getCharType(), charWithType.getChar());
        data.typedCharacter = typedCharWithType.getChar();
        data.elapsedTime = elapsedTime;
        data.isCorrect = isCorrect();

        return data;
    }
}
