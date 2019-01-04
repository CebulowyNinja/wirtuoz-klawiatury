package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phrase {
    private String phrase;

    public Phrase(String phrase) {
        this.phrase = phrase;
    }

    public static int countDiacriticChars(String word) {
        Pattern pattern = Pattern.compile("[^\\p{ASCII}]");
        Matcher matcher = pattern.matcher(word);

        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }

    public String getText() {
        return phrase;
    }

    public String[] getWords() {
        return phrase.split("\\s+");
    }

    public String[] getWordsWithDiacritics() {
        ArrayList<String> withDiacritic = new ArrayList<>();

        for (String word : getWords()) {
            if (countDiacriticChars(word) > 0) {
                withDiacritic.add(word);
            }
        }

        String[] withDiacriticArray = new String[withDiacritic.size()];
        return withDiacritic.toArray(withDiacriticArray);
    }

    public int getDiacriticCharsCount() {
        return countDiacriticChars(phrase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase phrase1 = (Phrase) o;
        return Objects.equals(phrase, phrase1.phrase);
    }

    @Override
    public int hashCode() {

        return Objects.hash(phrase);
    }
}
