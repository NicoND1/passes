package de.bytemc.passes.spigot;

import de.bytemc.ByteAPI;
import de.bytemc.passes.PassRepository;
import de.bytemc.passes.Passes;
import de.bytemc.passes.common.PassRepositoryImpl;
import de.bytemc.passes.common.PassesImpl;
import de.bytemc.passes.common.PlayersProvider;
import de.bytemc.passes.common.config.PassesConfig;
import de.bytemc.passes.common.config.PassesConfigLoader;
import de.bytemc.passes.common.user.ConnectionFactory;
import de.bytemc.passes.common.user.DatabasePassUserRepository;
import de.bytemc.passes.milestone.Milestone;
import de.bytemc.passes.spigot.command.BroadcastMilestoneCommand;
import de.bytemc.passes.user.PassUserRepository;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class PassesPlugin extends JavaPlugin implements PlayersProvider {

    @Override
    public void onEnable() {
        PassesConfig config = readConfig();
        PassRepository passRepository = new PassRepositoryImpl(config.getPasses());
        PassUserRepository userRepository = new DatabasePassUserRepository(getConnectionFactory(), passRepository);
        Passes passes = new PassesImpl(passRepository, userRepository, this);
        getServer().getServicesManager().register(Passes.class, passes, this, ServicePriority.Normal);

        for (Milestone milestone : config.getDefaultMilestones()) {
            passes.milestoneRepository().registerMilestone(milestone.getID(), milestone.getExp());
        }

        getCommand("broadcastmilestone").setExecutor(new BroadcastMilestoneCommand(passes));

        getLogger().info("Loaded " + passRepository.getPasses().size() + " passes");
    }

    private PassesConfig readConfig() {
        try {
            return PassesConfigLoader.readConfig(new File(getDataFolder(), "config.yml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        throw new RuntimeException("Can't reach this");
    }

    private ConnectionFactory getConnectionFactory() {
        return () -> ByteAPI.getInstance().getSqlAdapter().getSqlBaseExecutor().getConnection();
    }

    @Override
    public UUID[] getUUIDs() {
        return getServer().getOnlinePlayers().stream().map(Player::getUniqueId).toArray(UUID[]::new);
    }

    @Override
    public int getOnlineCount() {
        return getServer().getOnlinePlayers().size();
    }
}
