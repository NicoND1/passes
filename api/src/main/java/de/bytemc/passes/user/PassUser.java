package de.bytemc.passes.user;

import de.bytemc.passes.Collecting;
import de.bytemc.passes.Pass;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public interface PassUser extends Collecting {

    UUID getUUID();

    Collection<Pass> availablePasses();

    Collection<ActivePass> activePasses();

    void addPass(Pass pass);

}
