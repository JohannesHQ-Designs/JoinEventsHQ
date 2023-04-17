package com.nopermission.joineventshq.commands;

import com.nopermission.joineventshq.JoinEventsHQ;
import com.nopermission.joineventshq.configs.MessagesConfig;
import com.nopermission.joineventshq.spawn.SpawnManager;
import com.nopermission.joineventshq.spawn.models.Spawn;
import com.nopermission.joineventshq.utils.Text;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Command("joineventshq") @Alias({"joinevents", "je"})
public class JoinEventsHQCommand extends CommandBase {

    @Default @Permission("joineventsprohq.admin")
    public void defaultCommand(CommandSender commandSender) {
        List<String> strings = JoinEventsHQ.get().getMessage().helpMessage();
        if (strings.isEmpty())
            return;

        for (String string : strings) {
            commandSender.sendMessage(Text.formatComponent(string));
        }
    }

    @SubCommand("reload") @Permission("joineventsprohq.admin")
    public void reloadCommand(CommandSender commandSender) {
        JoinEventsHQ.get().reload();
        commandSender.sendMessage(Text.formatComponent("&aReloaded the plugin files and messages!"));
    }

    @SubCommand("spawns") @Permission("joineventsprohq.admin")
    public void listSpawnsCommand(CommandSender commandSender) {
        HashMap<String, Spawn> spawnHashMap = SpawnManager.get().getSpawnHashMap();
        if (spawnHashMap.isEmpty()) {
            commandSender.sendMessage(Text.formatComponent(JoinEventsHQ.get().getMessage().noSpawnsSet()));
            return;
        }

        spawnHashMap.forEach((s, spawn) -> {
            commandSender.sendMessage(Text.formatComponent("&eSpawn: &f" + s + " &7&o(loc: " + spawn.getLocation() + ")"));
        });
    }

    @SubCommand("addspawn") @Permission("joineventsprohq.admin")
    public void addSpawnsCommand(Player player, final String name) {
        if (name.isEmpty())
            return;

        HashMap<String, Spawn> spawnHashMap = SpawnManager.get().getSpawnHashMap();

        Optional<Map.Entry<String, Spawn>> first = spawnHashMap.entrySet().stream().filter(stringSpawnEntry -> stringSpawnEntry.getKey().equalsIgnoreCase(name)).findFirst();
        if (first.isPresent()) {
            player.sendMessage(Text.formatComponent("&cThis spawn already exists!"));
            return;
        }

        Location location = player.getLocation();
        Spawn spawn = new Spawn(name, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        spawn.save();
        SpawnManager.get().loadSpawns();

        player.sendMessage(Text.formatComponent("&aAdded the spawn: " + name));

    }

    @SubCommand("remove") @Permission("joineventsprohq.admin")
    public void removeSpawnsCommand(Player player, final String name) {
        if (name.isEmpty())
            return;

        HashMap<String, Spawn> spawnHashMap = SpawnManager.get().getSpawnHashMap();

        Optional<Map.Entry<String, Spawn>> first = spawnHashMap.entrySet().stream().filter(stringSpawnEntry -> stringSpawnEntry.getKey().equalsIgnoreCase(name)).findFirst();
        if (first.isEmpty()) {
            player.sendMessage(Text.formatComponent("&cThis spawn does not exists!"));
            return;
        }

        Map.Entry<String, Spawn> stringSpawnEntry = first.get();
        Spawn spawn = stringSpawnEntry.getValue();
        spawn.delete();
        player.sendMessage(Text.formatComponent("&cDeleted the spawn: " + name));

    }
}
