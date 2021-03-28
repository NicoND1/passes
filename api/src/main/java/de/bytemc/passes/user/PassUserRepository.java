package de.bytemc.passes.user;

import com.google.common.util.concurrent.ListenableFuture;
import de.bytemc.passes.Collectable;

import java.util.UUID;

/**
 * @author Nico_ND1
 */
public interface PassUserRepository {

    /**
     * Loads a {@link PassUser} from the database or gets it if already cached locally.
     *
     * @param uuid the {@link UUID} of the user
     * @return a {@link ListenableFuture future} storing the user
     */
    ListenableFuture<PassUser> loadUser(UUID uuid);

    /**
     * Gets a {@link PassUser} from the cache.
     *
     * @param uuid the {@link UUID} of the user
     * @return a {@link ListenableFuture future} storing the user or null
     */
    ListenableFuture<PassUser> getUser(UUID uuid);

    /**
     * Invalidate the {@link PassUser} bound to the given {@link UUID} from the cache.
     *
     * @param uuid the uuid of the user
     */
    void invalidateUser(UUID uuid);

    /**
     * {@link Collectable#disableCollecting() Disables collecting} of every {@link PassUser} matching a {@link UUID}
     * from the given array.
     *
     * @param uuids the array of {@link UUID uuids} to disable collecting
     */
    void disableCollecting(UUID... uuids);

}
