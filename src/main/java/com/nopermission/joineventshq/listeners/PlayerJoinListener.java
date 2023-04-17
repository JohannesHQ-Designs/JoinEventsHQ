package com.nopermission.joineventshq.listeners;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.utils.Text;
import com.nopermission.joineventshq.utils.Utils;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PlayerJoinListener implements Listener {

    private static JoinEventsHQ plugin;

    public PlayerJoinListener(JoinEventsHQ eventsHQ) {
        plugin = eventsHQ;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoinMotd(PlayerJoinEvent event) {
        if (!plugin.getConfiguration().motdEnabled())
            return;
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String s : plugin.getConfiguration().getJoinMotd()) {
                    player.sendMessage(Text.formatComponent(Utils.formatString(player, s)));
                }
                String joinSound = plugin.getConfiguration().getJoinSound();
                if (!joinSound.isEmpty())
                    Utils.playSound(player, joinSound);
            }
        }.runTaskLater(plugin, plugin.getConfiguration().motdDelay());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.getConfiguration().motdEnabled())
            return;
        Player player = event.getPlayer();
        List<String> joinCommands = plugin.getConfiguration().getJoinCommands();
        if (!joinCommands.isEmpty()) {
            for (String joinCommand : joinCommands) {
                player.performCommand(Utils.formatString(player, joinCommand));
            }
        }

        List<String> joinConsoleCommands = plugin.getConfiguration().getJoinConsoleCommands();
        if (!joinConsoleCommands.isEmpty()) {
            for (String joinCommand : joinConsoleCommands) {
                player.performCommand(Utils.formatString(player, joinCommand));
            }
        }

        if (plugin.getConfiguration().isTitleEnabled()) {
            Title title = Title.title(Text.formatComponent(plugin.getConfiguration().getJoinedTitle()), Text.formatComponent(plugin.getConfiguration().getJoinedSubTitle()));
            player.showTitle(title);
        }

        String joinMessage = plugin.getConfiguration().getJoinMessage();
        if (!joinMessage.isEmpty())
            event.joinMessage(Text.formatComponent(Utils.formatString(player, joinMessage)));
    }
}
