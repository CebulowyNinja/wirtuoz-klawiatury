package pl.olencki.jan.keyboardvirtuoso.game;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import pl.olencki.jan.keyboardvirtuoso.database.*;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;
import pl.olencki.jan.keyboardvirtuoso.game.exception.*;

import java.util.*;

public class CharGame extends Game {
    private HashSet<CharType> gameCharTypes = new HashSet<>();
    private ArrayList<Character> gameChars = new ArrayList<>();

    private Random randGenerator = new Random();

    public CharGame(CharType[] gameCharTypes, int challengesCount) {
        this.gameCharTypes.addAll(Arrays.asList(gameCharTypes));

        generateChars();
        generateChallenges(challengesCount);
    }

    public Set<CharType> getGameCharTypes() {
        return gameCharTypes;
    }

    public CharChallenge getRandomChallenge() {
        return new CharChallenge(new CharWithType(getRandomChar()));
    }

    public List<CharGameStatistics> getGameStatistics() {
        HashMap<CharType, CharGameStatistics> statisticsSet = new HashMap<>();

        for (Challenge challengeGeneric : challenges) {
            CharChallenge challenge = (CharChallenge) challengeGeneric;
            CharType charType = challenge.getCharWithType().getCharType();

            if (!statisticsSet.containsKey(charType)) {
                statisticsSet.put(charType, challenge.generateCharGameStatistics());
            } else {
                CharGameStatistics stats = statisticsSet.get(charType);
                stats = stats.sumStatistics(challenge.generateCharGameStatistics());
                statisticsSet.put(charType, stats);
            }

        }

        return new ArrayList<>(statisticsSet.values());
    }

    @Override
    public void addToDatabase(Context context, KeyboardData keyboard) {
        CharGameData gameData = new CharGameData(null, 0, new Date());
        List<CharChallenge> charChallenges = new ArrayList<>();
        for (Challenge challenge : challenges) {
            charChallenges.add((CharChallenge) challenge);
        }

        AddToDatabaseParams params = new AddToDatabaseParams(context, keyboard,
                gameData,
                charChallenges);
        new AddToDatabase().execute(params);
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

    private char getRandomChar() {
        int index = randGenerator.nextInt(gameChars.size());

        return gameChars.get(index);
    }


    private void generateChallenges(int challengesCount) {
        for (int i = 0; i < challengesCount; i++) {
            challenges.add(getRandomChallenge());
        }
    }

    private static class AddToDatabaseParams {
        Context context;
        KeyboardData keyboardData;
        CharGameData charGameData;
        List<CharChallenge> charChallenges;

        public AddToDatabaseParams(Context context, KeyboardData keyboardData,
                                   CharGameData charGameData, List<CharChallenge> charChallenges) {
            this.context = context;
            this.keyboardData = keyboardData;
            this.charGameData = charGameData;
            this.charChallenges = charChallenges;
        }
    }

    private static class AddToDatabase extends AsyncTask<AddToDatabaseParams, Integer, Integer> {
        @Override
        protected Integer doInBackground(AddToDatabaseParams... addToDatabaseParams) {
            for (AddToDatabaseParams params : addToDatabaseParams) {
                GamesDatabase db = GamesDatabase.getInstance(params.context);
                KeyboardDao keyboardDao = db.keyboardDao();
                CharGameDao gameDao = db.charGameDao();
                CharChallengeDao challengeDao = db.charChallengeDao();

                KeyboardData keyboardData = keyboardDao.findByClassName(
                        params.keyboardData.className);
                if (keyboardData == null) {
                    keyboardData = params.keyboardData;
                    keyboardData.id = keyboardDao.insert(keyboardData);
                } else {
                    keyboardData.name = params.keyboardData.name;
                    keyboardDao.update(keyboardData);
                }

                CharGameData gameData = params.charGameData;
                gameData.keyboardId = keyboardData.id;
                gameData.id = gameDao.insert(params.charGameData);

                List<CharChallengeData> challengesData = new ArrayList<>();
                for (CharChallenge challenge : params.charChallenges) {
                    try {
                        challengesData.add(challenge.generateCharChallengeData(null, gameData.id));
                    } catch (CharChallengeException e) {
                        Log.e("Database", e.toString());
                    }
                }

                challengeDao.insertMultiple(challengesData);
            }

            return 1;
        }
    }
}
