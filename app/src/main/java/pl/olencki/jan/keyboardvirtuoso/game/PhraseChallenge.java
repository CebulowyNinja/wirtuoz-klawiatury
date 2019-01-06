package pl.olencki.jan.keyboardvirtuoso.game;

import pl.olencki.jan.keyboardvirtuoso.database.entities.*;
import pl.olencki.jan.keyboardvirtuoso.game.exception.*;

public class PhraseChallenge extends Challenge {
    private Phrase phrase;
    private Phrase typedPhrase;

    public PhraseChallenge(Phrase phrase) {
        this.phrase = phrase;
    }

    public Phrase getPhrase() {
        return phrase;
    }

    public Phrase getTypedPhrase() {
        return typedPhrase;
    }

    public void setTypedPhrase(Phrase typedPhrase) {
        this.typedPhrase = typedPhrase;
    }

    @Override
    public boolean isCorrect() {
        return phrase.equals(typedPhrase);
    }


    public boolean[] areWordsCorrect() {
        String[] words = phrase.getWords();
        String text = typedPhrase.getText().trim();
        boolean[] areCorrect = new boolean[words.length];

        int uncorrectLength = 0;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i + 1 < words.length) {
                word = word + " ";
            }
            areCorrect[i] = text.startsWith(word);

            if (areCorrect[i]) {
                text = text.substring(word.length()).trim();
            } else if (i > 0 && !areCorrect[i - 1]) {
                int index = text.indexOf(word);
                if (index >= 0 && index < uncorrectLength * 3 / 2) {
                    areCorrect[i] = true;
                    text = text.substring(index + word.length()).trim();
                }
            }

            if (!areCorrect[i]) {
                uncorrectLength += word.length() + 2;
            } else {
                uncorrectLength = 0;
            }
        }

        return areCorrect;
    }

    public PhraseGameStatistics generatePhraseGameStatistics() {
        PhraseGameStatistics stats = new PhraseGameStatistics();

        stats.phrasesCount = 1;
        stats.correctPhrasesCount = isCorrect() ? 1 : 0;

        stats.totalLength = phrase.getText().length();
        stats.totalTypedLength = typedPhrase.getText().length();
        stats.elapsedTime = elapsedTime;

        stats.wordsCount = phrase.getWords().length;
        stats.wordsDiacriticCount = phrase.getWordsDiacritic().length;
        stats.correctWordsCount = correctWordsCount();
        stats.correctWordsDiacriticCount = correctWordsDiacriticCount();

        return stats;
    }

    public PhraseChallengeData generatePhraseChallengeData(Long id, long gameId) throws PhraseChallengeException {
        final String exceptionMsg = "Unable to create CharChallengeData object: ";
        PhraseChallengeData data;

        if (gameId <= 0) {
            throw new PhraseChallengeException(exceptionMsg + "gameId must be grather than 0");
        }

        if (typedPhrase == null) {
            throw new PhraseChallengeException(exceptionMsg + "typedPhrase can't be null");
        }

        data = new PhraseChallengeData(id, gameId, phrase.getText(), phrase.getText().length(),
                phrase.getWords().length,
                phrase.getWordsDiacritic().length);

        data.isCorrect = isCorrect();

        data.typedPhrase = typedPhrase.getText();
        data.typedPhraseLength = typedPhrase.getText().length();

        data.correctWordsCount = correctWordsCount();
        data.correctWordsDiacriticCount = correctWordsDiacriticCount();

        data.elapsedTime = elapsedTime;

        return data;
    }

    public int correctWordsCount() {
        int count = 0;
        for (boolean isCorrect : areWordsCorrect()) {
            if (isCorrect) {
                count++;
            }
        }

        return count;
    }

    public int correctWordsDiacriticCount() {
        int count = 0;
        String[] words = phrase.getWords();
        boolean[] areCorrect = areWordsCorrect();

        for (int i = 0; i < words.length; i++) {
            if (Phrase.countDiacriticChars(words[i]) > 0 && areCorrect[i]) {
                count++;
            }
        }

        return count;
    }
}
