package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CharGame {
    private HashSet<CharType> gameCharTypes = new HashSet<>();
    private ArrayList<Character> gameChars = new ArrayList<>();

    private ArrayList<CharChallenge> challenges = new ArrayList<>();

    private Random randGenerator = new Random();

    public CharGame(CharType[] gameCharTypes, int challengesCount) {
        this.gameCharTypes.addAll(Arrays.asList(gameCharTypes));

        generateChars();
        generateChallenges(challengesCount);
    }

    public List<CharChallenge> getChallenges() {
        return challenges;
    }

    public Set<CharType> getGameCharTypes() {
        return gameCharTypes;
    }

    public char getRandomChar() {
        int index = randGenerator.nextInt(gameChars.size());

        return gameChars.get(index);
    }

    private void generateChars() {
        for (CharType charType : gameCharTypes) {
            for (char ch : charType.getAllChars()) {
                if (!gameChars.contains(ch)) {
                    gameChars.add(ch);
                }
            }
        }
    }

    private void generateChallenges(int challengesCount) {
        for(int i = 0; i < challengesCount; i++) {
            challenges.add(new CharChallenge(new CharWithType(getRandomChar())));
        }
    }
}
