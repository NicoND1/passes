package de.bytemc.passes.spigot.command;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import de.bytemc.passes.Passes;
import de.bytemc.passes.milestone.MilestoneBroadcastResult;
import de.bytemc.passes.user.ActivePass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
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

        Futures.addCallback(passes.broadcastMilestone(id, uuids), new FutureCallback<MilestoneBroadcastResult>() {
            @Override
            public void onSuccess(MilestoneBroadcastResult result) {
                sender.sendMessage("Total uuids: " + result.getUUIDs().size());
                for (UUID uuid : result.getUUIDs()) {
                    Map<ActivePass, Integer> uuidResult = result.getResult(uuid);
                    if (uuidResult.isEmpty()) {
                        sender.sendMessage("Nothing changed for " + uuid);
                    } else {
                        sender.sendMessage("Changes for " + uuid + ":");

                        for (Map.Entry<ActivePass, Integer> entry : uuidResult.entrySet()) {
                            sender.sendMessage(entry.getKey().getPass().getID() + " = " + entry.getValue());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                sender.sendMessage("Error!");
                throwable.printStackTrace();
            }
        });
        sender.sendMessage("Broadcasted to " + String.join(", ", names));
        return true;
    }
}
