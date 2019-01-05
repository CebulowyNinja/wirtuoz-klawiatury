package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.Objects;

public class CharWithType {
    private char character;
    private CharType charType;

    public CharWithType(char character) {
        this.character = character;

        for (CharType charType : CharType.values()) {
            if (charType.isContainsChar(character)) {
                this.charType = charType;
            }
        }
    }

    public char getChar() {
        return character;
    }

    public CharType getCharType() {
        return charType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharWithType charWithType = (CharWithType) o;
        return character == charWithType.character;
    }

    @Override
    public int hashCode() {

        return Objects.hash(character);
    }
}
