package de.bytemc.passes;

import de.bytemc.passes.milestone.Milestone;
import de.bytemc.passes.milestone.MilestoneRepository;
import de.bytemc.passes.user.PassUserRepository;

import java.util.UUID;

/**
 * @author Nico_ND1
 */
public interface Passes {

    PassRepository passRepository();

    MilestoneRepository milestoneRepository();

    PassUserRepository passUserRepository();

    void broadcastMilestone(int id, UUID... uuids);

    default void broadcastMilestone(Milestone milestone, UUID... uuids) {
        broadcastMilestone(milestone.getID(), uuids);
    }

}
