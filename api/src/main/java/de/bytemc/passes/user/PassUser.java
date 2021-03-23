package de.bytemc.passes.user;

import de.bytemc.passes.Collectable;
import de.bytemc.passes.Pass;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public interface PassUser extends Collectable {

    UUID getUUID();

    Collection<ActivePass> activePasses();

    void addPass(Pass pass);

    void addExp(double exp);

}
