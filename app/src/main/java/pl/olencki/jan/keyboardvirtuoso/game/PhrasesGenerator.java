package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhrasesGenerator {
    private ArrayList<Phrase> phrases = new ArrayList<>();
    private Random randGenerator = new Random();

    public PhrasesGenerator(String[] phrasesString) {
        for(String phrase : phrasesString) {
            phrases.add(new Phrase(phrase));
        }
    }

    public List<Phrase> getPhrases() {
        return (List<Phrase>) phrases.clone();
    }

    public Phrase getRandomPhrase() {
        int index = randGenerator.nextInt(phrases.size());

        return phrases.get(index);
    }
}
