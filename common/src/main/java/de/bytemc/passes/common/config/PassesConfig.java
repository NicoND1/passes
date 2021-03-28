package de.bytemc.passes.common.config;

import de.bytemc.passes.Pass;
import de.bytemc.passes.milestone.Milestone;

import java.util.Set;

/**
 * @author Nico_ND1
 */
public class PassesConfig {

    private final boolean onlyCollect;
    private final Set<Pass> passes;
    private final Set<Milestone> defaultMilestones;

    protected PassesConfig(boolean onlyCollect, Set<Pass> passes, Set<Milestone> defaultMilestones) {
        this.onlyCollect = onlyCollect;
        this.passes = passes;
        this.defaultMilestones = defaultMilestones;
    }

    public boolean onlyCollect() {
        return onlyCollect;
    }

    public Set<Pass> getPasses() {
        return passes;
    }

    public Set<Milestone> getDefaultMilestones() {
        return defaultMilestones;
    }
}
