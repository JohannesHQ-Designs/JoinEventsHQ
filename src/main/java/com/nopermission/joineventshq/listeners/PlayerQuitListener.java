package com.nopermission.joineventshq.listeners;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.utils.Text;
import com.nopermission.joineventshq.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final JoinEventsHQ plugin = JoinEventsHQ.get();

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuitMessage(PlayerQuitEvent event) {
        String quitMessage = plugin.getConfiguration().getQuitMessage();
        if (!quitMessage.isEmpty())
            return;
        Player player = event.getPlayer();

        event.quitMessage(Text.formatComponent(Utils.formatString(player, quitMessage)));
    }
}
