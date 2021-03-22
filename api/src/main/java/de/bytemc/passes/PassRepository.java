package de.bytemc.passes;

import java.util.Collection;

/**
 * @author Nico_ND1
 */
public interface PassRepository {

    Collection<Pass> getPasses();

    Pass getPass(int id);

}
