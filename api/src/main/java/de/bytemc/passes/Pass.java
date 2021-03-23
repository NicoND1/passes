package de.bytemc.passes;

import de.bytemc.passes.icon.Displayable;
import de.bytemc.passes.level.PassLevel;
import de.bytemc.passes.level.PassLevelConfiguration;

import java.util.Collection;

/**
 * @author Nico_ND1
 */
public interface Pass extends Collectable, Displayable {

    /**
     * Get this id.
     *
     * @return the id of this {@link Pass}
     */
    int getID();

    /**
     * Get all levels.
     *
     * @return an unmodifiable collection of {@link PassLevel}
     */
    Collection<PassLevel> getLevels();

    /**
     * Get a {@link PassLevel} with it's level value.
     *
     * @param level the level
     * @return the {@link PassLevel} if found or null
     */
    PassLevel getLevel(int level);

    @Deprecated
    PassLevelConfiguration levelConfiguration();

}
