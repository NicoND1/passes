package de.bytemc.passes.milestone;

import java.util.Optional;

/**
 * @author Nico_ND1
 */
public interface MilestoneRepository {

    /**
     * Register a new {@link Milestone} with the given id and exp. If the id is already present, the previous milestone
     * bound to it will be replaced.
     *
     * @param id  the id of the milestone
     * @param exp the exp for it
     * @return the {@link Milestone} registered
     */
    Milestone registerMilestone(int id, double exp);

    /**
     * Register a new {@link Milestone} with the given id and zero exp. If the id is already present, the previous
     * milestone bound to it will be replaced.
     *
     * @param id the id of the milestone
     * @return the {@link Milestone} registered
     */
    default Milestone registerMilestone(int id) {
        return registerMilestone(id, 0.0D);
    }

    /**
     * Get a {@link Milestone} with the given id.
     *
     * @param id the id
     * @return a {@link Optional} which may contains the milestone with the given id
     */
    Optional<Milestone> getMilestone(int id);

    /**
     * Update the {@link Milestone#getExp()} from a {@link Milestone} with the given id.
     *
     * @param id  the id
     * @param exp the new amount of exp
     */
    void updateMilestoneExp(int id, double exp);

    /**
     * Get the {@link Milestone#getExp()} from a {@link Milestone} with the given id.
     *
     * @param id the id
     * @return the amount of exp or zero if the milestone wasn't found
     */
    double getMilestoneExp(int id);

}
