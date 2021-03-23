package de.bytemc.passes.common;

import de.bytemc.passes.icon.Icon;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.level.PassLevelConfiguration;

import java.util.Set;
import java.util.StringJoiner;

/**
 * @author Nico_ND1
 */
public class DefaultPass extends AbstractPass {

    private boolean collecting = true;

    public DefaultPass(int id, Icon icon, Set<PassLevel> levels, PassLevelConfiguration levelConfiguration) {
        super(id, icon, levels, levelConfiguration);
    }

    @Override
    public PassType getType() {
        return PassType.DEFAULT;
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
