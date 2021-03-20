package de.bytemc.passes.milestone;

import java.util.Optional;

/**
 * @author Nico_ND1
 */
public interface MilestoneRepository {

    Milestone registerMilestone(int id, double exp);

    default Milestone registerMilestone(int id) {
        return registerMilestone(id, 0.0D);
    }

    Optional<Milestone> getMilestone(int id);

    void updateMilestoneExp(int id, double exp);

    double getMilestoneExp(int id);

}
