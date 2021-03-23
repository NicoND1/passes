package de.bytemc.passes.reward;

import de.bytemc.passes.icon.Displayable;
import de.bytemc.passes.user.PassUser;

/**
 * @author Nico_ND1
 */
public interface Reward extends Displayable {

    /**
     * Collect this reward to the given {@link PassUser}.
     *
     * @param passUser the user to give this reward to
     */
    void collect(PassUser passUser);

}
