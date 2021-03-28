package de.bytemc.passes;

import com.google.common.util.concurrent.ListenableFuture;
import de.bytemc.passes.milestone.Milestone;
import de.bytemc.passes.milestone.MilestoneBroadcastResult;
import de.bytemc.passes.milestone.MilestoneRepository;
import de.bytemc.passes.payment.Payment;
import de.bytemc.passes.payment.PaymentRepository;
import de.bytemc.passes.user.PassUser;
import de.bytemc.passes.user.PassUserRepository;

import java.util.UUID;

/**
 * @author Nico_ND1
 */
public interface Passes {

    /**
     * Get the repository where {@link Pass passes} are registered.
     *
     * @return the {@link PassRepository}
     */
    PassRepository passRepository();

    /**
     * Get the repository where {@link Milestone milestones} are registered.
     *
     * @return the {@link MilestoneRepository}
     */
    MilestoneRepository milestoneRepository();

    /**
     * Get the repository where {@link PassUser users} are registered.
     *
     * @return the {@link PassUserRepository}
     */
    PassUserRepository passUserRepository();

    /**
     * Get the repository where {@link Payment payments} are registered.
     *
     * @return the {@link PaymentRepository}
     */
    PaymentRepository paymentRepository();

    /**
     * Broadcasts a {@link Milestone} to every {@link PassUser} from the given array of uuids or, if empty, every online
     * user.
     *
     * @param id    the {@link Milestone#getID() id} of a registered {@link Milestone}
     * @param uuids the array of {@link UUID uuids} to use, or empty for every online user
     */
    ListenableFuture<MilestoneBroadcastResult> broadcastMilestone(int id, UUID... uuids);

    default ListenableFuture<MilestoneBroadcastResult> broadcastMilestone(Milestone milestone, UUID... uuids) {
        return broadcastMilestone(milestone.getID(), uuids);
    }

    // TODO: javadoc
    boolean onlyCollect();

    void setOnlyCollect(boolean onlyCollect);

}
