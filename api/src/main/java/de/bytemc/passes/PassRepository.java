package de.bytemc.passes;

import java.util.Collection;

/**
 * @author Nico_ND1
 */
public interface PassRepository {

    /**
     * Get every pass registered.
     *
     * @return an unmodifiable {@link Collection} with every registered {@link Pass}
     */
    Collection<Pass> getPasses();

    /**
     * Get a pass by it's id.
     *
     * @param id the {@link Pass#getID() id} of a registered {@link Pass}
     * @return the, if found, {@link Pass} or null
     */
    Pass getPass(int id);

}
