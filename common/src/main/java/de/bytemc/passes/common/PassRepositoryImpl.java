package de.bytemc.passes.common;

import de.bytemc.passes.Pass;
import de.bytemc.passes.PassRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author Nico_ND1
 */
public class PassRepositoryImpl implements PassRepository {

    private final Set<Pass> passes;

    public PassRepositoryImpl(Set<Pass> passes) {
        this.passes = passes;
    }

    @Override
    public Collection<Pass> getPasses() {
        return Collections.unmodifiableCollection(passes);
    }

    @Override
    public Pass getPass(int id) {
        return null;
    }
}
