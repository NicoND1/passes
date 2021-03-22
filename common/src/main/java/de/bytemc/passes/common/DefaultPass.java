package de.bytemc.passes.common;

import de.bytemc.passes.PassLevel;
import de.bytemc.passes.icon.Icon;

import java.util.Set;
import java.util.StringJoiner;

/**
 * @author Nico_ND1
 */
public class DefaultPass extends AbstractPass {

    private boolean collecting = true;

    protected DefaultPass(int id, Icon icon, Set<PassLevel> levels) {
        super(id, icon, levels);
    }

    @Override
    public boolean isCollecting() {
        return collecting;
    }

    @Override
    public void enableCollecting() {
        collecting = true;
    }

    @Override
    public void disableCollecting() {
        collecting = false;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DefaultPass.class.getSimpleName() + "[", "]")
            .add("collecting=" + collecting)
            .add("id=" + id)
            .add("icon=" + icon)
            .add("levels=" + levels)
            .toString();
    }
}
