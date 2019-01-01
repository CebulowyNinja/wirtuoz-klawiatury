package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.Arrays;

public enum CharType {
    ALPHA_LOWER {
        @Override
        char[] generateChars() {
            return generateCharsFromRange('a', 'z');
        }
    },
    ALPHA_UPPER {
        @Override
        char[] generateChars() {
            return generateCharsFromRange('A', 'Z');
        }
    },
    DIGIT {
        @Override
        char[] generateChars() {
            return generateCharsFromRange('0', '9');
        }
    },
    SPECIAL {
        @Override
        char[] generateChars() {
            return new char[]{
                    '!', '@', '#', '$'
            };
        }
    },
    SPECIAL_MATH {
        @Override
        char[] generateChars() {
            return new char[]{
                    '+', '-', '*', '/'
            };
        }
    };

    private final char[] chars;

    CharType() {
        chars = generateChars();
        Arrays.sort(chars);
    }

    abstract char[] generateChars();

    protected char[] generateCharsFromRange(char start, char end) {
        char[] chars = new char[end - start];

        for (char ch = start; ch <= end; ch++) {
            chars[ch - start] = ch;
        }

        return chars;
    }

    char[] getAllChars() {
        return Arrays.copyOf(chars, chars.length);
    }

    boolean isContainsChar(char ch) {
        return Arrays.binarySearch(chars, ch) >= 0;
    }
}
