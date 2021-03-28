package de.bytemc.passes.user;

import de.bytemc.passes.Collectable;
import de.bytemc.passes.Pass;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public interface PassUser extends Collectable {

    /**
     * Get the {@link UUID}.
     *
     * @return the uuid
     */
    UUID getUUID();

    /**
     * Get every active (f.e. bought) {@link ActivePass} of this {@link PassUser}.
     *
     * @return an unmodifiable {@link Collection}
     */
    Collection<ActivePass> activePasses();

    /**
     * Adds the given {@link Pass} to this user's passes.
     *
     * @param pass the pass to add
     */
    void addPass(Pass pass);

    /**
     * Add experience points to every {@link ActivePass} from {@link PassUser#activePasses()}.
     *
     * @param exp the amount of exp to add
     * @return a {@link Map} where the amount of added levels is bound to an active pass
     */
    Map<ActivePass, Integer> addExp(double exp);

    void updateActivePass(ActivePass activePass);

}
