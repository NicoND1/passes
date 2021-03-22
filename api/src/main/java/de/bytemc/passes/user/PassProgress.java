package de.bytemc.passes.user;

import java.util.StringJoiner;

/**
 * @author Nico_ND1
 */
public class PassProgress {

    private int level;
    private double exp;

    public PassProgress(int level, double exp) {
        this.level = level;
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public double getExp() {
        return exp;
    }

    void update(int level, double exp) {
        this.level = level;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PassProgress.class.getSimpleName() + "[", "]")
            .add("level=" + level)
            .add("exp=" + exp)
            .toString();
    }
}
