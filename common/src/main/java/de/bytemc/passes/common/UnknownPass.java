package de.bytemc.passes.common;

import de.bytemc.passes.Pass;
import de.bytemc.passes.icon.Icon;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.level.PassLevelConfiguration;

import java.util.Collection;

/**
 * @author Nico_ND1
 */
public class UnknownPass implements Pass {
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Collection<PassLevel> getLevels() {
        return null;
    }

    @Override
    public PassLevel getLevel(int level) {
        return null;
    }

    @Override
    public PassLevelConfiguration levelConfiguration() {
        return null;
    }

    @Override
    public boolean isCollecting() {
        return false;
    }

    @Override
    public void enableCollecting() {

    }

    @Override
    public void disableCollecting() {

    }

    @Override
    public Icon getIcon() {
        return null;
    }
}
