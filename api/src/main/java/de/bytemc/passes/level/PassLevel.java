package de.bytemc.passes.level;

import com.google.common.base.Objects;
import de.bytemc.passes.payment.Payment;

import java.util.Set;
import java.util.StringJoiner;

/**
 * @author Nico_ND1
 */
public class PassLevel {

    private final int level;
    private final double neededExp;
    private final Set<Payment> rewards;

    public PassLevel(int level, double neededExp, Set<Payment> rewards) {
        this.level = level;
        this.neededExp = neededExp;
        this.rewards = rewards;
    }

    public int getLevel() {
        return level;
    }

    public double getNeededExp() {
        return neededExp;
    }

    public Set<Payment> getRewards() {
        return rewards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassLevel passLevel = (PassLevel) o;
        return getLevel() == passLevel.getLevel();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLevel());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PassLevel.class.getSimpleName() + "[", "]")
            .add("level=" + level)
            .add("neededExp=" + neededExp)
            .add("rewards=" + rewards)
            .toString();
    }
}
