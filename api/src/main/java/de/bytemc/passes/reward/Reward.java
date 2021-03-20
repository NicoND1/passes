package de.bytemc.passes.reward;

import de.bytemc.passes.icon.Displayable;
import de.bytemc.passes.user.PassUser;

/**
 * @author Nico_ND1
 */
public interface Reward extends Displayable {

    void collect(PassUser passUser);

}
