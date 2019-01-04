package pl.olencki.jan.keyboardvirtuoso.game;

import pl.olencki.jan.keyboardvirtuoso.game.exception.CharChallengeException;
import pl.olencki.jan.keyboardvirtuoso.gamesdata.CharChallengeData;

public class CharChallenge {
    private CharWithType charWithType;
    private CharWithType typedCharWithType;
    private float elapsedTime;

    public CharChallenge(CharWithType charWithType) {
        this.charWithType = charWithType;
    }

    public CharWithType getCharWithType() {
        return charWithType;
    }

    public void setCharWithType(CharWithType charWithType) {
        this.charWithType = charWithType;
    }

    public CharWithType getTypedCharWithType() {
        return typedCharWithType;
    }

    public void setTypedCharWithType(CharWithType typedCharWithType) {
        this.typedCharWithType = typedCharWithType;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public boolean isCorrect() {
        return charWithType.equals(typedCharWithType);
    }

    public CharChallengeData generateCharChallengeData(Long id, long gameId) throws CharChallengeException {
        final String exceptionMsg = "Unable to create CharChallengeData object: ";
        CharChallengeData data;

        if(gameId <= 0) {
            throw new CharChallengeException(exceptionMsg + "gameId must be grather than 0");
        }

        if(charWithType.getCharType() == null) {
            throw new CharChallengeException(exceptionMsg + "unknown charType in charWithType");
        }

        if(typedCharWithType == null) {
            throw new CharChallengeException(exceptionMsg + "typedCharWithType can't be null");
        }

        if(typedCharWithType.getCharType() == null) {
            throw new CharChallengeException(exceptionMsg + "unknown charType in typedCharWithType");
        }

        data = new CharChallengeData(id, gameId,
                                     charWithType.getCharType(), charWithType.getChar());
        data.typedCharacter = typedCharWithType.getChar();
        data.elapsedTime = elapsedTime;
        data.isCorrect = charWithType.equals(typedCharWithType);

        return data;
    }
}
