package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CharsGenerator {
    private HashSet<CharType> charTypes = new HashSet<>();
    private ArrayList<Character> chars = new ArrayList<>();
    private Random randGenerator = new Random();

    public CharsGenerator(CharType[] charTypesArray) {
        charTypes.addAll(Arrays.asList(charTypesArray));

        generateChars();
    }

    public List<Character> getChars() {
        return (List<Character>) chars.clone();
    }

    public char getRandomChar() {
        int index = randGenerator.nextInt(chars.size());

        return chars.get(index);
    }

    private void generateChars() {
        for(CharType charType : charTypes) {
            for(char ch : charType.getAllChars()) {
                if(!chars.contains(ch)) {
                    chars.add(ch);
                }
            }
        }
    }
}
