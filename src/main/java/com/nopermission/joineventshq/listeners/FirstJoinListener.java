package com.nopermission.joineventshq.listeners;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.utils.Text;
import com.nopermission.joineventshq.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class FirstJoinListener implements Listener {

    private final JoinEventsHQ plugin = JoinEventsHQ.get();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore())
            return;

        String firstJoinMessage = plugin.getConfiguration().getFirstJoinMessage();
        if (!firstJoinMessage.isEmpty())
            plugin.getServer().broadcast(Text.formatComponent(Utils.formatString(player, firstJoinMessage)),  "joineventshq.firstjoin");

        String firstJoinSound = plugin.getConfiguration().getFirstJoinSound();
        if (!firstJoinSound.isEmpty())
            Utils.playSound(player, firstJoinSound);

        List<String> firstJoinCommands = plugin.getConfiguration().getFirstJoinCommands();
        if (!firstJoinCommands.isEmpty()) {
            for (String joinCommand : firstJoinCommands) {
                player.performCommand(Utils.formatString(player, joinCommand));
            }
        }

        List<String> firstJoinConsoleCommands = plugin.getConfiguration().getFirstJoinConsoleCommands();
        if (!firstJoinConsoleCommands.isEmpty()) {
            for (String joinCommand : firstJoinConsoleCommands) {
                player.performCommand(Utils.formatString(player, joinCommand));
            }
        }
    }

}
