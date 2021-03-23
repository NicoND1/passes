package de.bytemc.passes.common;

import java.util.UUID;

/**
 * @author Nico_ND1
 */
public interface PlayersProvider {

    UUID[] getUUIDs();

    int getOnlineCount();

}
