package pl.olencki.jan.keyboardvirtuoso.game.exception;

public class ChallengeTimerException extends Exception {
    public ChallengeTimerException() {
        super();
    }

    public ChallengeTimerException(String message) {
        super(message);
    }

    public ChallengeTimerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChallengeTimerException(Throwable cause) {
        super(cause);
    }
}
