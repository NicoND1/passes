package de.bytemc.passes.icon;

import java.text.MessageFormat;

/**
 * @author Nico_ND1
 */
class FancyColorString {

    private final String string;
    private final MessageFormat format;

    FancyColorString(String string) {
        if (string != null) {
            this.format = new MessageFormat(string);
        } else {
            this.format = null;
        }
        this.string = string;
    }

    String getString(Object... args) {
        return format == null ? null : format.format(args);
    }

    String getRawString() {
        return string;
    }

}
