package de.bytemc.passes.spigot.listener;

import de.bytemc.passes.user.PassUserRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Nico_ND1
 */
public class CacheListener implements Listener {

    private final PassUserRepository userRepository;

    public CacheListener(PassUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        userRepository.loadUser(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        userRepository.invalidateUser(event.getPlayer().getUniqueId());
    }

}
