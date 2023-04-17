package com.nopermission.joineventshq.utils;

import com.nopermission.joineventshq.JoinEventsHQ;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public class Utils {

    public static String formatString(Player player, String string) {
        if (JoinEventsHQ.get().hasPlugin("PlaceholderAPI"))
            string = formatString(player, string);

        string = string.replace("<player>", player.getName());
        string = string.replace("<uuid>", player.getUniqueId().toString());
        return string;
    }

    public static void playSound(Player player, String soundName) {
        Optional<Sound> optionalSound = Arrays.stream(Sound.values()).filter(sound -> sound.name().equalsIgnoreCase(soundName)).findFirst();

        if (optionalSound.isEmpty())
            return;

        player.playSound(player.getLocation(), optionalSound.get(), 1F, 1F);

    }

}
