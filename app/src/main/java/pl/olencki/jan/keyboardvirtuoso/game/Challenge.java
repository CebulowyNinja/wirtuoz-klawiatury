package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.Date;

import pl.olencki.jan.keyboardvirtuoso.game.exception.ChallengeTimerException;

public abstract class Challenge {
    protected float elapsedTime;
    private Date startDate;

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void startMeasureTime() {
        startDate = new Date();
    }

    public void stopMeasureTime() throws ChallengeTimerException {
        Date stopDate = new Date();

        if (startDate == null) {
            throw new ChallengeTimerException("Time measuring isn't started.");
        }

        elapsedTime += (stopDate.getTime() - startDate.getTime()) / 1000f;
    }

    public abstract boolean isCorrect();

    public abstract String getText();

    public abstract String getTypedText();
}
