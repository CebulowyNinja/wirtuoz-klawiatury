package pl.olencki.jan.keyboardvirtuoso.game;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.olencki.jan.keyboardvirtuoso.gamesdata.*;

public abstract class Game {
    protected ArrayList<Challenge> challenges = new ArrayList<>();
    protected int currentChallengeIndex = 0;

    @SuppressWarnings("unchecked")
    public List<Challenge> getChallenges() {
        return (List<Challenge>) challenges.clone();
    }

    public int getChallengesCount() {
        return challenges.size();
    }

    public int getCurrentChallengeIndex() {
        return currentChallengeIndex;
    }

    public Challenge getCurrentChallenge() {
        return challenges.get(currentChallengeIndex);
    }

    public boolean hasNextChallenge() {
        return currentChallengeIndex + 1 < challenges.size();
    }

    public Challenge nextChallenge() {
        if (!hasNextChallenge()) {
            return null;
        }

        currentChallengeIndex++;
        return getCurrentChallenge();
    }

    public void generateNewCurrentChallenge() {
        challenges.set(currentChallengeIndex, getRandomChallenge());
    }

    abstract public Challenge getRandomChallenge();

    abstract public void addToDatabase(Context context, KeyboardData keyboard);
}
