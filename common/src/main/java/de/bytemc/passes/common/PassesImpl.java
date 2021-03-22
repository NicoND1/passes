package de.bytemc.passes.common;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import de.bytemc.passes.PassRepository;
import de.bytemc.passes.Passes;
import de.bytemc.passes.common.milestone.MilestoneRepositoryImpl;
import de.bytemc.passes.milestone.MilestoneRepository;
import de.bytemc.passes.user.PassUser;
import de.bytemc.passes.user.PassUserRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class PassesImpl implements Passes {

    private final PassRepository passes;
    private final MilestoneRepository milestones = new MilestoneRepositoryImpl();
    private final PassUserRepository users;

    public PassesImpl(PassRepository passes, PassUserRepository users) {
        this.passes = passes;
        this.users = users;
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
    public void broadcastMilestone(int id, UUID... uuids) {
        if (uuids == null || uuids.length == 0) {
            uuids = Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).toArray(UUID[]::new);
        }

        double exp = milestones.getMilestoneExp(id);
        if (exp <= 0.0D) {
            return;
        }

        for (UUID uuid : uuids) {
            Futures.addCallback(users.getUser(uuid), new FutureCallback<PassUser>() {
                @Override
                public void onSuccess(PassUser passUser) {
                    passUser.addExp(exp);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }
}
