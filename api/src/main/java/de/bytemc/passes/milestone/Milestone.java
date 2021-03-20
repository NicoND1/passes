package de.bytemc.passes.milestone;

import com.google.common.base.Objects;

import java.util.StringJoiner;

/**
 * @author Nico_ND1
 */
public class Milestone {

    private final int id;
    private double exp;

    public Milestone(int id) {
        this(id, 0.0D);
    }

    public Milestone(int id, double exp) {
        this.id = id;
        this.exp = exp;
    }

    public int getID() {
        return id;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Milestone that = (Milestone) o;
        return getID() == that.getID();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getID());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Milestone.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("exp=" + exp)
            .toString();
    }
}
