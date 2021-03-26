package de.bytemc.passes.common;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import de.bytemc.passes.PassRepository;
import de.bytemc.passes.Passes;
import de.bytemc.passes.common.milestone.MilestoneRepositoryImpl;
import de.bytemc.passes.milestone.MilestoneBroadcastResult;
import de.bytemc.passes.milestone.MilestoneRepository;
import de.bytemc.passes.payment.PaymentRepository;
import de.bytemc.passes.user.ActivePass;
import de.bytemc.passes.user.PassUser;
import de.bytemc.passes.user.PassUserRepository;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class PassesImpl implements Passes {

    private final PassRepository passes;
    private final MilestoneRepository milestones = new MilestoneRepositoryImpl();
    private final PassUserRepository users;
    private final PlayersProvider playersProvider;
    private final PaymentRepository payments;

    public PassesImpl(PassRepository passes, PassUserRepository users, PlayersProvider playersProvider, PaymentRepository payments) {
        this.passes = passes;
        this.users = users;
        this.playersProvider = playersProvider;
        this.payments = payments;
    }

    @Override
    public PassRepository passRepository() {
        return passes;
    }

    @Override
    public MilestoneRepository milestoneRepository() {
        return milestones;
    }

    @Override
    public PassUserRepository passUserRepository() {
        return users;
    }

    @Override
    public PaymentRepository paymentRepository() {
        return payments;
    }

    @Override
    public ListenableFuture<MilestoneBroadcastResult> broadcastMilestone(int id, UUID... uuids) {
        SettableFuture<MilestoneBroadcastResult> result = SettableFuture.create();
        if (uuids == null || uuids.length == 0) {
            uuids = playersProvider.getUUIDs();
        }

        double exp = milestones.getMilestoneExp(id);
        if (exp <= 0.0D) {
            result.set(new MilestoneBroadcastResult(Collections.emptyMap()));
            return result;
        }

        Map<UUID, Map<ActivePass, Integer>> players = Maps.newConcurrentMap();
        int amountNeeded = uuids.length;
        for (UUID uuid : uuids) {
            Futures.addCallback(users.getUser(uuid), new FutureCallback<PassUser>() {
                @Override
                public void onSuccess(PassUser passUser) {
                    players.put(uuid, passUser.addExp(exp));
                    progress();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                    players.put(uuid, Collections.emptyMap());
                    progress();
                }

                private void progress() {
                    if (players.size() >= amountNeeded) {
                        result.set(new MilestoneBroadcastResult(players));
                    }
                }
            });
        }

        return result;
    }
}
