package de.bytemc.passes;

import java.util.Date;

/**
 * @author Nico_ND1
 */
public interface EventPass extends Pass {

    /**
     * Get the {@link Date} from where this {@link EventPass} is collecting from.
     *
     * @return the {@link Date}
     */
    Date getStart();

    /**
     * Get the {@link Date} to where this {@link EventPass} is collecting to.
     *
     * @return the {@link Date}
     */
    Date getEnd();

}
