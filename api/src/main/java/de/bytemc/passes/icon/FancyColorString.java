package de.bytemc.passes.icon;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Nico_ND1
 */
class FancyColorString {

    private static final String INDICATOR = "§?";

    private final String string;
    private final Set<Integer> indices;

    FancyColorString(String string) {
        this.indices = new HashSet<>();

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];

            if (i != chars.length - 1) {
                char nextChar = chars[i + 1];

                StringBuilder combiner = new StringBuilder().append(ch).append(nextChar);
                if (combiner.toString().equals(INDICATOR)) {
                    indices.add(++i);

                }
            }

            stringBuilder.append(ch);
        }

        this.string = stringBuilder.toString();
    }

    String getString(char colorCode) {
        StringBuilder builder = new StringBuilder(string);
        int skips = 0;
        for (int index : indices) {
            builder.insert(index + skips++, colorCode);
        }
        return builder.toString();
    }

}
