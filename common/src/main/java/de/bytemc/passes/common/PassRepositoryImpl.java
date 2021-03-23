package de.bytemc.passes.common;

import de.bytemc.passes.Pass;
import de.bytemc.passes.PassRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Nico_ND1
 */
public class PassRepositoryImpl implements PassRepository {

    private final Map<Integer, Pass> passes;

    public PassRepositoryImpl(Set<Pass> passes) {
        this.passes = new HashMap<>();

        for (Pass pass : passes) {
            this.passes.put(pass.getID(), pass);
        }
    }

    @Override
    public Collection<Pass> getPasses() {
        return passes.values();
    }

    @Override
    public Pass getPass(int id) {
        return passes.get(id);
    }
}
