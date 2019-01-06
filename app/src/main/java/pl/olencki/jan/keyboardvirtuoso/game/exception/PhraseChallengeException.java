package pl.olencki.jan.keyboardvirtuoso.game.exception;

public class PhraseChallengeException extends Exception {
    public PhraseChallengeException() {
    }

    public PhraseChallengeException(String message) {
        super(message);
    }

    public PhraseChallengeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhraseChallengeException(Throwable cause) {
        super(cause);
    }
}
