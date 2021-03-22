package de.bytemc.passes.user;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.UUID;

/**
 * @author Nico_ND1
 */
public interface PassUserRepository {

    ListenableFuture<PassUser> loadUser(UUID uuid);

    ListenableFuture<PassUser> getUser(UUID uuid);

    void disableCollecting(UUID... uuids);

}
