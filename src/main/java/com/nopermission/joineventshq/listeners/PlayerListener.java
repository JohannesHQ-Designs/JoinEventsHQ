package com.nopermission.joineventshq.listeners;

import com.cryptomorin.xseries.XSound;
import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.utils.Text;
import com.nopermission.joineventshq.utils.Utils;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PlayerListener implements Listener {

    private static JoinEventsHQ plugin;

    public PlayerListener(JoinEventsHQ eventsHQ) {
        plugin = eventsHQ;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore())
            return;

        String firstJoinMessage = plugin.getConfiguration().getFirstJoinMessage();
        if (!firstJoinMessage.isEmpty())
            plugin.getServer().broadcast(Text.formatComponent(Utils.formatString(player, firstJoinMessage)));

        String firstJoinSound = plugin.getConfiguration().getFirstJoinSound();
        if (!firstJoinSound.isEmpty())
            XSound.matchXSound(firstJoinSound).ifPresent(xSound -> xSound.play(player));

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
                    XSound.matchXSound(joinSound).ifPresent(xSound -> xSound.play(player));
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
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoinMessage(PlayerJoinEvent event) {
        String joinMessage = plugin.getConfiguration().getJoinMessage();
        if (!joinMessage.isEmpty())
            return;
        Player player = event.getPlayer();

        event.joinMessage(Text.formatComponent(Utils.formatString(player, joinMessage)));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuitMessage(PlayerQuitEvent event) {
        String quitMessage = plugin.getConfiguration().getQuitMessage();
        if (!quitMessage.isEmpty())
            return;
        Player player = event.getPlayer();

        event.quitMessage(Text.formatComponent(Utils.formatString(player, quitMessage)));
    }
}
