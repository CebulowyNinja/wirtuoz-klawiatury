package pl.olencki.jan.keyboardvirtuoso.game;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import pl.olencki.jan.keyboardvirtuoso.database.*;
import pl.olencki.jan.keyboardvirtuoso.database.entities.*;
import pl.olencki.jan.keyboardvirtuoso.game.exception.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PhraseGame extends Game {
    private String[] phrasesString;

    private Random randGenerator = new Random();

    public PhraseGame(String[] phrasesString, int challengesCount) {
        this.phrasesString = phrasesString;

        generateChallenges(challengesCount);
    }

    public PhraseChallenge getRandomChallenge() {
        return new PhraseChallenge(getRandomPhrase());
    }

    public PhraseGameStatistics getGameStatistics() {
        PhraseGameStatistics totalStats = new PhraseGameStatistics();

        for (Challenge challengeGeneric : challenges) {
            PhraseChallenge challenge = (PhraseChallenge) challengeGeneric;
            totalStats = totalStats.sumStatistics(challenge.generatePhraseGameStatistics());
        }

        return totalStats;
    }

    @Override
    public void addToDatabase(Context context, KeyboardData keyboard) {
        PhraseGameData gameData = new PhraseGameData(null, 0, new Date());
        List<PhraseChallenge> phraseChallenges = new ArrayList<>();
        for (Challenge challenge : challenges) {
            phraseChallenges.add((PhraseChallenge) challenge);
        }

        AddToDatabaseParams params = new AddToDatabaseParams(context, keyboard,
                gameData, phraseChallenges);
        new AddToDatabase().execute(params);
    }

    private Phrase getRandomPhrase() {
        int index = randGenerator.nextInt(phrasesString.length);

        return new Phrase(phrasesString[index]);
    }


    private void generateChallenges(int challengesCount) {
        for (int i = 0; i < challengesCount; i++) {
            challenges.add(getRandomChallenge());
        }
    }

    private static class AddToDatabaseParams {
        Context context;
        KeyboardData keyboardData;
        PhraseGameData phraseGameData;
        List<PhraseChallenge> phraseChallenges;

        public AddToDatabaseParams(Context context, KeyboardData keyboardData,
                                   PhraseGameData phraseGameData, List<PhraseChallenge> phraseChallenges) {
            this.context = context;
            this.keyboardData = keyboardData;
            this.phraseGameData = phraseGameData;
            this.phraseChallenges = phraseChallenges;
        }
    }

    private static class AddToDatabase extends AsyncTask<AddToDatabaseParams, Integer, Integer> {
        @Override
        protected Integer doInBackground(AddToDatabaseParams... addToDatabaseParams) {
            for (AddToDatabaseParams params : addToDatabaseParams) {
                GamesDatabase db = GamesDatabase.getInstance(params.context);
                KeyboardDao keyboardDao = db.keyboardDao();
                PhraseGameDao gameDao = db.phraseGameDao();
                PhraseChallengeDao challengeDao = db.phraseChallengeDao();

                KeyboardData keyboardData = keyboardDao.findByClassName(
                        params.keyboardData.className);
                if (keyboardData == null) {
                    keyboardData = params.keyboardData;
                    keyboardData.id = keyboardDao.insert(keyboardData);
                } else {
                    keyboardData.name = params.keyboardData.name;
                    keyboardDao.update(keyboardData);
                }

                PhraseGameData gameData = params.phraseGameData;
                gameData.keyboardId = keyboardData.id;
                gameData.id = gameDao.insert(params.phraseGameData);

                List<PhraseChallengeData> challengesData = new ArrayList<>();
                for (PhraseChallenge challenge : params.phraseChallenges) {
                    try {
                        challengesData.add(
                                challenge.generatePhraseChallengeData(null, gameData.id));
                    } catch (PhraseChallengeException e) {
                        Log.e("Database", e.toString());
                    }
                }

                challengeDao.insertMultiple(challengesData);
            }

            return 1;
        }
    }
}
