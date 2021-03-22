package de.bytemc.passes;

import de.bytemc.passes.icon.Displayable;

import java.util.Collection;

/**
 * @author Nico_ND1
 */
public interface Pass extends Collecting, Displayable {

    int getID();

    Collection<PassLevel> getLevels();

    PassLevel getLevel(int level);

}
