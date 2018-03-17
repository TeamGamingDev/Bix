package de.biosphere.bix.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Biosphere
 * @date 23.01.18
 */
public class StringUtils {

    /**
     * Forbidden characters
     */
    private static final List<Character> ESCAPE_CHARACTERS = new ArrayList<Character>() {{
        this.add('*');
        this.add('_');
        this.add('`');
        this.add('~');
    }};

    /**
     * Replace all forbidden characters
     */
    public static String replaceCharacter(final String input) {
        final StringBuilder stringBuilder = new StringBuilder(input);
        for (int i = 0; i < stringBuilder.length(); i++) {
            final char c = stringBuilder.charAt(i);
            if (ESCAPE_CHARACTERS.contains(c)) {
                stringBuilder.replace(i, i + 1, "\\" + c);
                i += 1;
            }
        }
        return stringBuilder.toString().replace("'", " ");
    }

}
