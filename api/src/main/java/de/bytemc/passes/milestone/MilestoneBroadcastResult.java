package de.bytemc.passes.milestone;

import de.bytemc.passes.user.ActivePass;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class MilestoneBroadcastResult {

    private final Map<UUID, Map<ActivePass, Integer>> internalMap;

    public MilestoneBroadcastResult(Map<UUID, Map<ActivePass, Integer>> internalMap) {
        this.internalMap = internalMap;
    }

    public Collection<UUID> getUUIDs() {
        return internalMap.keySet();
    }

    public Map<ActivePass, Integer> getResult(UUID uuid) {
        return internalMap.getOrDefault(uuid, Collections.emptyMap());
    }

}
