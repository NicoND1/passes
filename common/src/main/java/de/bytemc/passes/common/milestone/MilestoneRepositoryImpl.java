package de.bytemc.passes.common.milestone;

import com.google.common.collect.Maps;
import de.bytemc.passes.milestone.Milestone;
import de.bytemc.passes.milestone.MilestoneRepository;

import java.util.Map;
import java.util.Optional;

/**
 * @author Nico_ND1
 */
public class MilestoneRepositoryImpl implements MilestoneRepository {

    private final Milestone emptyMilestone = new Milestone(-1, 0.0D);
    private final Map<Integer, Milestone> milestones = Maps.newConcurrentMap();

    @Override
    public Milestone registerMilestone(int id, double exp) {
        Milestone milestone = new Milestone(id, exp);
        milestones.put(id, milestone);
        return milestone;
    }

    @Override
    public Optional<Milestone> getMilestone(int id) {
        return Optional.ofNullable(milestones.get(id));
    }

    @Override
    public void updateMilestoneExp(int id, double exp) {
        getMilestone(id).ifPresent(milestone -> milestone.setExp(exp));
    }

    @Override
    public double getMilestoneExp(int id) {
        return milestones.getOrDefault(id, emptyMilestone).getExp();
    }
}
