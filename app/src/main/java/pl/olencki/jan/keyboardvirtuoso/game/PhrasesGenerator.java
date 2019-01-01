package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhrasesGenerator {
    private List<Phrase> phrases = new ArrayList<>();
    private Random randGenerator = new Random();

    public PhrasesGenerator(String[] phrasesString) {
        for(String phrase : phrasesString) {
            phrases.add(new Phrase(phrase));
        }
    }

    public Phrase[] getAllPhrases() {
        Phrase[] phrasesArray = new Phrase[phrases.size()];

        return phrases.toArray(phrasesArray);
    }

    public Phrase getRandomPhrase() {
        int index = randGenerator.nextInt(phrases.size());

        return phrases.get(index);
    }
}
