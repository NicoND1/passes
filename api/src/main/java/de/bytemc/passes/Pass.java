package de.bytemc.passes;

import de.bytemc.passes.icon.Displayable;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.level.PassLevelConfiguration;

import java.util.Collection;

/**
 * @author Nico_ND1
 */
public interface Pass extends Collectable, Displayable {

    int getID();

    Collection<PassLevel> getLevels();

    PassLevel getLevel(int level);

    PassLevelConfiguration levelConfiguration();

}
