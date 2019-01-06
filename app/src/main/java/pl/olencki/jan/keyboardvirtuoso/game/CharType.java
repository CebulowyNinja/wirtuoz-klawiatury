package pl.olencki.jan.keyboardvirtuoso.game;

import java.util.Arrays;

public enum CharType {
    ALPHA_LOWER {
        @Override
        protected char[] generateChars() {
            return generateCharsFromRange('a', 'z');
        }
    },
    ALPHA_UPPER {
        @Override
        protected char[] generateChars() {
            return generateCharsFromRange('A', 'Z');
        }
    },
    DIGIT {
        @Override
        protected char[] generateChars() {
            return generateCharsFromRange('0', '9');
        }
    },
    SPECIAL {
        @Override
        protected char[] generateChars() {
            return new char[]{
                    '!', '@', '$', '.', '?', ',', '-', '+', '=', '_'
            };
        }
    };

    private final char[] chars;

    CharType() {
        chars = generateChars();
        Arrays.sort(chars);
    }

    public char[] getAllChars() {
        return Arrays.copyOf(chars, chars.length);
    }

    public boolean isContainsChar(char ch) {
        return Arrays.binarySearch(chars, ch) >= 0;
    }

    abstract protected char[] generateChars();

    protected char[] generateCharsFromRange(char start, char end) {
        char[] chars = new char[end - start + 1];

        for (char ch = start; ch <= end; ch++) {
            chars[ch - start] = ch;
        }

        return chars;
    }
}
