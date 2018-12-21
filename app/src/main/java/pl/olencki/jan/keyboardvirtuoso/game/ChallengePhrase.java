package pl.olencki.jan.keyboardvirtuoso.game;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChallengePhrase {
    private String phrase;
    private String[] words;

    public ChallengePhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public String[] getWords() {
        if(words == null) {
            words = phrase.split("\\s+");
        }
        
        return words;
    }

    public String[] getWordsWithDiacritic() {
        ArrayList<String> withDiacritic = new ArrayList<String>();
        for(String word: getWords()) {
            if(countDiacriticChars(word) > 0) {
                withDiacritic.add(word);
            }
        }

        String[] wordsWithDiacritic = new String[withDiacritic.size()];
        return withDiacritic.toArray(wordsWithDiacritic);
    }

    public static int countDiacriticChars(String word) {
        Pattern pattern = Pattern.compile("[^\\p{ASCII}]");
        Matcher matcher = pattern.matcher(word);

        int count = 0;
        while(matcher.find()) {
            count++;
        }

        return count;
    }
}
