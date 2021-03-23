package de.bytemc.passes.common.user;

import com.google.common.base.Objects;
import de.bytemc.passes.Pass;
import de.bytemc.passes.user.ActivePass;
import de.bytemc.passes.user.PassProgress;
import de.bytemc.passes.user.PassUser;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class PassUserImpl implements PassUser {

    private final DatabasePassUserRepository userRepository;
    private final UUID uuid;
    private final Set<ActivePass> activePasses;
    private boolean collecting = true;

    public PassUserImpl(DatabasePassUserRepository userRepository, UUID uuid, Set<ActivePass> activePasses) {
        this.userRepository = userRepository;
        this.uuid = uuid;
        this.activePasses = activePasses;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Collection<ActivePass> activePasses() {
        return Collections.unmodifiableCollection(activePasses);
    }

    @Override
    public void addPass(Pass pass) {
        ActivePass activePass = new ActivePass(this, pass, new PassProgress(1, 0.0D), new HashSet<>());
        activePasses.add(activePass);
        userRepository.insertPass(activePass);
    }

    @Override
    public Map<ActivePass, Integer> addExp(double exp) {
        if (!isCollecting()) {
            return Collections.emptyMap();
        }

        Map<ActivePass, Integer> differences = new HashMap<>();
        for (ActivePass activePass : activePasses) {
            if (activePass.getPass().isCollecting()) {
                differences.put(activePass, activePass.addExp(exp));
                userRepository.update(activePass);
                // TODO: Rethink this logic of updating everytime
            }
        }
        return differences;
    }

    @Override
    public boolean isCollecting() {
        return collecting;
    }

    @Override
    public void enableCollecting() {
        collecting = true;
    }

    @Override
    public void disableCollecting() {
        collecting = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassUserImpl passUser = (PassUserImpl) o;
        return Objects.equal(uuid, passUser.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PassUserImpl.class.getSimpleName() + "[", "]")
            .add("uuid=" + uuid)
            .add("activePasses=" + activePasses)
            .add("collecting=" + collecting)
            .toString();
    }
}
