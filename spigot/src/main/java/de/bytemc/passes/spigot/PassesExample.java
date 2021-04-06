package de.bytemc.passes.spigot;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import de.bytemc.passes.Passes;
import de.bytemc.passes.milestone.DefaultMilestones;
import de.bytemc.passes.milestone.MilestoneRepository;
import de.bytemc.passes.user.PassUser;
import de.bytemc.passes.user.PassUserRepository;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class PassesExample {

    public PassesExample() {
        // Get instance and milestone repository
        Passes passes = Bukkit.getServicesManager().load(Passes.class);
        MilestoneRepository milestones = passes.milestoneRepository();
        // Register milestone for destroyed beds which gives 1.5 exp
        milestones.registerMilestone(BedWarsMilestones.BED_DESTROY, 1.5D);

        // Broadcast bed destroy milestone to every player currently collecting (f.e. ingame)
        passes.broadcastMilestone(BedWarsMilestones.BED_DESTROY);
        // Broadcast win milestone to a player
        passes.broadcastMilestone(DefaultMilestones.ROUND_WIN, UUID.randomUUID());

        // Get user repository
        PassUserRepository users = passes.passUserRepository();
        // Disable collecting for players (f.e. when they are out of game)
        users.disableCollecting(UUID.randomUUID(), UUID.randomUUID());

        // Enable collecting for a player
        Futures.addCallback(users.getUser(UUID.randomUUID()), new FutureCallback<PassUser>() {
            @Override
            public void onSuccess(PassUser passUser) {
                if (passUser != null) {
                    passUser.enableCollecting();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public static class BedWarsMilestones {

        public static final int BED_DESTROY = 10;

    }

}
