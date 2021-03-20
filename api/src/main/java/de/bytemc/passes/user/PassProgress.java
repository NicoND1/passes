package de.bytemc.passes.user;

import java.util.StringJoiner;

/**
 * @author Nico_ND1
 */
public class PassProgress {

    private int level;
    private double exp;

    PassProgress(int level, double exp) {
        this.level = level;
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
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
