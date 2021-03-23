package de.bytemc.passes.spigot.command;

import de.bytemc.passes.Passes;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class BroadcastMilestoneCommand implements CommandExecutor {

    private final Passes passes;

    public BroadcastMilestoneCommand(Passes passes) {
        this.passes = passes;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 && args.length != 2) {
            sender.sendMessage("Benutzung: /" + label + " <ID> [Names,]");
            return false;
        }

        int id = Integer.parseInt(args[0]);
        String[] names = args.length == 2 ? args[1].split(",") : new String[] {sender.getName()};
        UUID[] uuids = new UUID[names.length];
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Player player = Bukkit.getPlayer(name);
            if (player != null) {
                uuids[i] = player.getUniqueId();
            }
        }

        passes.broadcastMilestone(id, uuids);
        sender.sendMessage("Broadcasted to " + String.join(", ", names));
        return true;
    }
}
