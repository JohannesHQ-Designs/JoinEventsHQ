package com.nopermission.joineventshq.utils;

import com.nopermission.joineventshq.JoinEventsHQ;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class Utils {

    public static String formatString(Player player, String string) {
        if (JoinEventsHQ.get().hasPlugin("PlaceholderAPI"))
            string = formatString(player, string);

        string.replace("%player%", player.getName());
        string.replace("%uuid%", player.getUniqueId().toString());
        return string;
    }

}
